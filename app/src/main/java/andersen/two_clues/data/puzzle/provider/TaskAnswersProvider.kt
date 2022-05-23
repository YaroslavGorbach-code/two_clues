package andersen.two_clues.data.puzzle.provider

import andersen.two_clues.R
import andersen.two_clues.data.common.model.PuzzleName
import android.content.Context

class TaskAnswersProvider(private val context: Context) {
    companion object {
        private const val WORD_SPLIT_SYMBOL = "|"
    }

    fun provide(name: PuzzleName): List<List<String>> {
        return when (name) {
            PuzzleName.TEST -> context.resources.getStringArray(
                R.array.task_answers_letter_a
            ).toList().map { it.split(WORD_SPLIT_SYMBOL) }
        }
    }
}