package andersen.two_clues.data.puzzles.factory

import andersen.two_clues.R
import andersen.two_clues.data.common.model.PuzzleName
import android.content.Context

class PuzzleDescriptionProvider(private val context: Context) {

    fun provide(puzzleName: PuzzleName): String {
        return when (puzzleName) {
            PuzzleName.LETTER_A -> context.resources.getString(R.string.letter_a_description)
            PuzzleName.LETTER_B -> context.resources.getString(R.string.letter_b_description)
        }
    }
}