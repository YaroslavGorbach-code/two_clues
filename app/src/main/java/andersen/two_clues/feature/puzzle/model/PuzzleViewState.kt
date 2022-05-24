package andersen.two_clues.feature.puzzle.model

import andersen.two_clues.data.puzzle.model.Puzzle
import andersen.two_clues.utills.UiMessage


data class PuzzleViewState(
    val puzzle: Puzzle?,
    val currentTask: Puzzle.Task? = null,
    val isHintByAdAvailable: Boolean = false,
    val message: UiMessage<PuzzleUiMessage>?,
    val isAnswerCorrectVisible: Boolean = false,
    val isAnswerInCorrectVisible: Boolean = false,
) {

    companion object {

        val Test = PuzzleViewState(
            message = null,
            puzzle = null
        )
    }
}
