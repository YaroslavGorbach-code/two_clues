package andersen.two_clues.feature.puzzles.model

import andersen.two_clues.data.puzzles.model.PuzzleItem
import andersen.two_clues.utills.UiMessage

data class PuzzlesViewState(
    val puzzleItems: List<PuzzleItem>,
    val message: UiMessage<PuzzlesUiMessage>?,
) {
    companion object {
        val Test = PuzzlesViewState(
            puzzleItems = ArrayList(),
            message = null,
        )
    }
}
