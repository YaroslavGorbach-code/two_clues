package andersen.two_clues.data.puzzle.provider

import andersen.two_clues.R
import andersen.two_clues.data.common.model.PuzzleName

class TaskCuesResIdProvider {
    fun provide(name: PuzzleName): Int {
        return when (name) {
            PuzzleName.TEST -> R.string.task_letter_a
        }
    }
}