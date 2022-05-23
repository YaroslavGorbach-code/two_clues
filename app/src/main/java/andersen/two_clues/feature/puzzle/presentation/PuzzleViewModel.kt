package andersen.two_clues.feature.puzzle.presentation

import andersen.two_clues.PUZZLE_NAME_ARG
import andersen.two_clues.data.common.PuzzleRepo
import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzle.model.Puzzle
import andersen.two_clues.feature.puzzle.model.PuzzleAction
import andersen.two_clues.feature.puzzle.model.PuzzleUiMessage
import andersen.two_clues.feature.puzzle.model.PuzzleViewState
import andersen.two_clues.utills.AdManager
import andersen.two_clues.utills.UiMessage
import andersen.two_clues.utills.UiMessageManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PuzzleViewModel @Inject constructor(
    private val puzzleRepo: PuzzleRepo,
    private val adManager: AdManager,
    savedState: SavedStateHandle
) : ViewModel() {
    val puzzleName: PuzzleName = savedState[PUZZLE_NAME_ARG]!!

    private val pendingActions = MutableSharedFlow<PuzzleAction>()

    private var puzzle: MutableStateFlow<Puzzle?> = MutableStateFlow(null)

    private val uiMessageManager: UiMessageManager<PuzzleUiMessage> = UiMessageManager()

    val state: StateFlow<PuzzleViewState> = combine(
        puzzle,
        uiMessageManager.message,
    ) { puzzle, message ->
        PuzzleViewState(
            puzzle = puzzle,
            message = message,
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = PuzzleViewState.Test
    )

    init {
        adManager.loadRewordAd()

        viewModelScope.launch {
            loadPuzzle(puzzleName)
        }

        viewModelScope.launch {
            pendingActions.collect { action ->
                when (action) {

                    PuzzleAction.CheckAnswer -> {

                    }

                    is PuzzleAction.RequestShowRewordAd -> {
                        uiMessageManager.emitMessage(
                            UiMessage(PuzzleUiMessage.ShowRewardAd)
                        )
                    }

                    is PuzzleAction.ShowRewordAd -> {
                        adManager.showRewardAd(
                            activity = action.activity,
                        ) {

                        }
                    }
                }
            }
        }
    }

    private suspend fun loadPuzzle(name: PuzzleName) {
        puzzleRepo.getPuzzle(name)
            .flowOn(Dispatchers.IO)
            .collect(puzzle::emit)
    }

    fun submitAction(action: PuzzleAction) {
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



