package andersen.two_clues.feature.puzzle.model

import andersen.two_clues.data.puzzle.model.Puzzle
import andersen.two_clues.utills.UiMessage


data class PuzzleViewState(
    val puzzle: Puzzle?,
    val isHintByAdAvailable: Boolean = false,
    val message: UiMessage<PuzzleUiMessage>?,
) {
    companion object {
        val Test = PuzzleViewState(
            message = null,
            puzzle = null
        )
    }
}
