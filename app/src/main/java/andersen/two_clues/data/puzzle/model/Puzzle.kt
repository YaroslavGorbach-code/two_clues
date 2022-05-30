package andersen.two_clues.data.puzzle.model

import andersen.two_clues.data.common.model.PuzzleName

data class Puzzle(
    val name: PuzzleName,
    val task: List<Task>,
) {

    data class Task(
        val clues: Pair<String, String>,
        val correctAnswer: List<Letter>,
        val correctAnswerString: String,
        val maxMyAnswerSize: Int = correctAnswer.size,
        var myAnswer: List<Letter> = ArrayList(),
        val letters: List<Letter>,
        val orderNumber: Int,
    ) {

        fun checkAnswer(): Boolean {
            return correctAnswerString.lowercase().trim() == myAnswer.map { it.char }
                .joinToString("").lowercase()
        }

        data class Letter(
            val id: Int,
            val char: Char,
            val isUsed: Boolean = false,
            val isDisplayed: Boolean = false
        )

    }
}

fun List<Puzzle.Task.Letter>.use(id: Int, isUsed: Boolean): List<Puzzle.Task.Letter> {
    return map { letter ->
        if (letter.id == id) {
            letter.copy(isUsed = isUsed)
        } else {
            letter
        }
    }
}

fun List<Puzzle.Task>.getNextOrNull(current: Puzzle.Task): Puzzle.Task? {
    val indexOfCurrent = indexOfFirst { it.orderNumber == current.orderNumber }
    return getOrNull(indexOfCurrent.inc())
}
