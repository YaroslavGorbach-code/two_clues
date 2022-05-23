package andersen.two_clues.data.puzzle.model

import andersen.two_clues.data.common.model.PuzzleName

data class Puzzle(
    val name: PuzzleName,
    val task: List<Task>,
) {

    data class Task(
        val clues: Pair<String, String>,
        val correctAnswer: List<String>,
        val letters: List<Letter>
    ) {
        data class Letter(val char: Char, val isUsed: Boolean = false)
    }

}

