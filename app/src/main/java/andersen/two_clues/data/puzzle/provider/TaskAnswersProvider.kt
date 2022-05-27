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
            PuzzleName.LETTER_A -> context.resources.getStringArray(
                R.array.task_answers_letter_a
            ).toList().map { it.split(WORD_SPLIT_SYMBOL) }

            PuzzleName.LETTER_B -> context.resources.getStringArray(
                R.array.task_answers_letter_b
            ).toList().map { it.split(WORD_SPLIT_SYMBOL) }

            PuzzleName.LETTER_C -> context.resources.getStringArray(
                R.array.task_answers_letter_c
            ).toList().map { it.split(WORD_SPLIT_SYMBOL) }

            PuzzleName.LETTER_D -> context.resources.getStringArray(
                R.array.task_answers_letter_d
            ).toList().map { it.split(WORD_SPLIT_SYMBOL) }

            PuzzleName.LETTER_E ->  context.resources.getStringArray(
                R.array.task_answers_letter_e
            ).toList().map { it.split(WORD_SPLIT_SYMBOL) }

            PuzzleName.LETTER_F -> context.resources.getStringArray(
                R.array.task_answers_letter_f
            ).toList().map { it.split(WORD_SPLIT_SYMBOL) }
        }
    }
}