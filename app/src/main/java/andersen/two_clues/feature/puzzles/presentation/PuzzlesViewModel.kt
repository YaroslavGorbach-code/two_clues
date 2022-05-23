package andersen.two_clues.feature.puzzles.presentation

import andersen.two_clues.data.common.PuzzleRepo
import andersen.two_clues.data.puzzles.model.PuzzleItem
import andersen.two_clues.feature.puzzles.model.PuzzlesAction
import andersen.two_clues.feature.puzzles.model.PuzzlesUiMessage
import andersen.two_clues.feature.puzzles.model.PuzzlesViewState
import andersen.two_clues.utills.AdManager
import andersen.two_clues.utills.UiMessage
import andersen.two_clues.utills.UiMessageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PuzzlesViewModel @Inject constructor(
    private val puzzleRepo: PuzzleRepo,
    private val adManager: AdManager
) : ViewModel() {

    private val pendingActions = MutableSharedFlow<PuzzlesAction>()

    private val uiMessageManager: UiMessageManager<PuzzlesUiMessage> = UiMessageManager()

    private val puzzles: MutableStateFlow<List<PuzzleItem>> = MutableStateFlow(emptyList())

    val state: StateFlow<PuzzlesViewState> = combine(
        puzzles,
        uiMessageManager.message,
    ) { puzzles, message ->
        PuzzlesViewState(puzzleItems = puzzles, message = message)
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = PuzzlesViewState.Test
    )

    init {
        adManager.loadRewordAd()

        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {
                    is PuzzlesAction.ShowPuzzleUnAvailableDialog -> {
                        uiMessageManager.emitMessage(
                            UiMessage(
                                PuzzlesUiMessage.ShowPuzzleUnAvailableDialog(
                                    action.name
                                )
                            )
                        )
                    }
                    PuzzlesAction.LoadPuzzle -> {
                        loadPuzzles()
                    }
                    is PuzzlesAction.RequestShowRewordAd -> {
                        uiMessageManager.emitMessage(
                            UiMessage(
                                PuzzlesUiMessage.ShowRewardAd(
                                    action.puzzleName
                                )
                            )
                        )
                    }
                    is PuzzlesAction.ShowRewordAd -> {
                        adManager.showRewardAd(
                            activity = action.activity,
                        ) {
                            viewModelScope.launch {
                                puzzleRepo.makeAvailable(action.puzzleName)
                                loadPuzzles()
                            }
                        }
                    }

                    PuzzlesAction.ShowHelpDialog -> {
                        uiMessageManager.emitMessage(UiMessage(PuzzlesUiMessage.ShowHelpDialog))
                    }
                }
            }
        }
    }

    private suspend fun loadPuzzles() {
        puzzleRepo.getPuzzleItems().collect(puzzles::emit)
    }

    fun submitAction(action: PuzzlesAction) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }
}



