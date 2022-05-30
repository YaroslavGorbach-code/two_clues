package andersen.two_clues.feature.puzzle.presentation

import andersen.two_clues.PUZZLE_NAME_ARG
import andersen.two_clues.data.common.PuzzleRepo
import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzle.model.Puzzle
import andersen.two_clues.data.puzzle.model.getNextOrNull
import andersen.two_clues.data.puzzle.model.use
import andersen.two_clues.feature.puzzle.model.PuzzleAction
import andersen.two_clues.feature.puzzle.model.PuzzleUiMessage
import andersen.two_clues.feature.puzzle.model.PuzzleViewState
import andersen.two_clues.utills.AdManager
import andersen.two_clues.utills.UiMessage
import andersen.two_clues.utills.UiMessageManager
import android.util.Log
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

    private val puzzle: MutableStateFlow<Puzzle?> = MutableStateFlow(null)

    private val currentTask: MutableStateFlow<Puzzle.Task?> = MutableStateFlow(null)

    private var isAnswerCorrectVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val isAnswerIncorrectVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val uiMessageManager: UiMessageManager<PuzzleUiMessage> = UiMessageManager()

    val state: StateFlow<PuzzleViewState> = combine(
        puzzle,
        currentTask,
        uiMessageManager.message,
        isAnswerCorrectVisible,
        isAnswerIncorrectVisible,
    ) { puzzle, currentTask, message, isAnswerCorrectVisible, isAnswerIncorrectVisible ->

        PuzzleViewState(
            puzzle = puzzle,
            message = message,
            currentTask = currentTask,
            isAnswerCorrectVisible = isAnswerCorrectVisible,
            isAnswerInCorrectVisible = isAnswerIncorrectVisible,
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
                        currentTask.value?.let { task ->
                            isAnswerCorrectVisible.emit(task.checkAnswer())
                            isAnswerIncorrectVisible.emit(task.checkAnswer().not())

                            if (task.checkAnswer() && isCurrentTskLast()) {
                                uiMessageManager.emitMessage(UiMessage(PuzzleUiMessage.ShowWinDialog))
                                puzzleRepo.finishPuzzle(name = puzzleName)
                            }
                        }
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
                            currentTask.update { task ->
                                task?.copy(myAnswer = task.correctAnswerString.mapIndexed { index, char ->
                                    Puzzle.Task.Letter(id = index, char)
                                })
                            }
                        }
                    }
                    is PuzzleAction.ChoseLetter -> {
                        currentTask.update { task ->
                            if (task?.maxMyAnswerSize == task?.myAnswer?.size) {
                                task
                            } else {
                                task?.copy(
                                    letters = task.letters.use(action.letter.id, true),
                                    myAnswer = task.myAnswer.toMutableList()
                                        .apply {
                                            add(action.letter)

                                            val nextIndex = size.inc()
                                            val spaces =
                                                task.correctAnswer.filter { it.char.isWhitespace() }
                                            val indexes =
                                                spaces.map { task.correctAnswer.indexOf(it) }
                                            if (indexes.any { it == nextIndex }) {
                                                add(
                                                    Puzzle.Task.Letter(0, " ".toCharArray().first())
                                                )
                                            }
                                        }
                                )
                            }
                        }
                    }
                    is PuzzleAction.RemoveLetter -> {
                        action.letter?.let { currentLetter ->
                            currentTask.update { task ->
                                task?.copy(
                                    letters = task.letters.use(action.letter.id, false),
                                    myAnswer = task.myAnswer.toMutableList()
                                        .apply { remove(currentLetter) }
                                )
                            }
                        }
                    }
                    PuzzleAction.NextTask -> {
                        currentTask.value?.let { currentTask ->
                            puzzle.value?.task?.getNextOrNull(currentTask)?.let { task ->
                                this@PuzzleViewModel.currentTask.emit(task)
                            }
                        }
                        isAnswerCorrectVisible.emit(false)
                    }
                    PuzzleAction.RevealWord -> {
                        uiMessageManager.emitMessage(UiMessage(PuzzleUiMessage.ShowAdWarningDialog))
                    }
                }
            }
        }
    }

    private fun isCurrentTskLast() = currentTask.value?.orderNumber == puzzle.value?.task?.size

    private suspend fun loadPuzzle(name: PuzzleName) {
        puzzleRepo.getPuzzle(name)
            .flowOn(Dispatchers.IO)
            .collect { puzzle ->
                this.puzzle.emit(puzzle)
                currentTask.emit(puzzle.task[0])
            }
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



