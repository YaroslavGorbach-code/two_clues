package andersen.two_clues.data.puzzles.factory

import andersen.two_clues.R
import andersen.two_clues.data.common.model.PuzzleName
import android.content.Context

class PuzzleStoryProvider(private val context: Context) {

    fun provide(puzzleName: PuzzleName): String {
        return when (puzzleName) {
            PuzzleName.TEST -> context.resources.getString(R.string.app_name)
        }
    }
}