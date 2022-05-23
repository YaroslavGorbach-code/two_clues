package andersen.two_clues.data.puzzles.model

import andersen.two_clues.data.common.model.PuzzleName

data class PuzzleItem(
    val name: PuzzleName,
    val description: String,
    val isAvailable: Boolean,
    val isCompleted: Boolean
) {

}