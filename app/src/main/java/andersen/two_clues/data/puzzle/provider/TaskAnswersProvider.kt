package andersen.two_clues.data.puzzle.provider

import andersen.two_clues.R
import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzle.model.Puzzle
import android.content.Context

class TaskAnswersProvider(private val context: Context) {
    fun provide(name: PuzzleName): List<String> {
        return when (name) {
            PuzzleName.LETTER_A -> context.resources.getStringArray(
                R.array.task_answers_letter_a
            ).toList()

            PuzzleName.LETTER_B -> context.resources.getStringArray(
                R.array.task_answers_letter_b
            ).toList()

            PuzzleName.LETTER_C -> context.resources.getStringArray(
                R.array.task_answers_letter_c
            ).toList()

            PuzzleName.LETTER_D -> context.resources.getStringArray(
                R.array.task_answers_letter_d
            ).toList()

            PuzzleName.LETTER_E ->  context.resources.getStringArray(
                R.array.task_answers_letter_e
            ).toList()

            PuzzleName.LETTER_F -> context.resources.getStringArray(
                R.array.task_answers_letter_f
            ).toList()

            PuzzleName.LETTER_G -> context.resources.getStringArray(
                R.array.task_answers_letter_g
            ).toList()

            PuzzleName.LETTER_H -> context.resources.getStringArray(
                R.array.task_answers_letter_h
            ).toList()

            PuzzleName.LETTER_L -> context.resources.getStringArray(
                R.array.task_answers_letter_l
            ).toList()

            PuzzleName.LETTER_M -> context.resources.getStringArray(
                R.array.task_answers_letter_m
            ).toList()

            PuzzleName.LETTER_N -> context.resources.getStringArray(
                R.array.task_answers_letter_n
            ).toList()

            PuzzleName.LETTER_O -> context.resources.getStringArray(
                R.array.task_answers_letter_o
            ).toList()

            PuzzleName.LETTER_P -> context.resources.getStringArray(
                R.array.task_answers_letter_p
            ).toList()

            PuzzleName.LETTER_R -> context.resources.getStringArray(
                R.array.task_answers_letter_r
            ).toList()

            PuzzleName.LETTER_S -> context.resources.getStringArray(
                R.array.task_answers_letter_s
            ).toList()

            PuzzleName.LETTER_T -> context.resources.getStringArray(
                R.array.task_answers_letter_t
            ).toList()
        }
    }
}