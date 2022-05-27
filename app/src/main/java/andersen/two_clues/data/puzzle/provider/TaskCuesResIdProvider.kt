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
        }
    }
}