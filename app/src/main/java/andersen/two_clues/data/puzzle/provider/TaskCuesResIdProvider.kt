package andersen.two_clues.data.puzzle.provider

import andersen.two_clues.R
import andersen.two_clues.data.common.model.PuzzleName

class TaskCuesResIdProvider {
    fun provide(name: PuzzleName): Int {
        return when (name) {
            PuzzleName.LETTER_A -> R.string.task_letter_a
            PuzzleName.LETTER_B -> R.string.task_letter_b
            PuzzleName.LETTER_C -> R.string.task_letter_c
            PuzzleName.LETTER_D -> R.string.task_letter_d
            PuzzleName.LETTER_E -> R.string.task_letter_e
            PuzzleName.LETTER_F -> R.string.task_letter_f
            PuzzleName.LETTER_G -> R.string.task_letter_g
            PuzzleName.LETTER_H -> R.string.task_letter_h
            PuzzleName.LETTER_L -> R.string.task_letter_l
            PuzzleName.LETTER_M -> R.string.task_letter_m
            PuzzleName.LETTER_N -> R.string.task_letter_n
            PuzzleName.LETTER_O -> R.string.task_letter_o
            PuzzleName.LETTER_P -> R.string.task_letter_p
            PuzzleName.LETTER_R ->  R.string.task_letter_r
            PuzzleName.LETTER_S -> R.string.task_letter_s
            PuzzleName.LETTER_T -> R.string.task_letter_t
        }
    }
}