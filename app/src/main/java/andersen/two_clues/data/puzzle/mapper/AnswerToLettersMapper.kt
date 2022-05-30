package andersen.two_clues.data.puzzle.mapper

import andersen.two_clues.data.puzzle.model.Puzzle
import java.util.*

class AnswerToLettersMapper() {

    companion object {
        private const val DEFAULT_LETTERS_SIZE = 16
        private val ALPHABET = "abcdefghijklmnopqrstuvwxyz"
    }

    fun map(answer: String): List<Puzzle.Task.Letter> {
        val answerLetters: Queue<Char> =
            ArrayDeque(answer.toList().filterNot { it.toString() == " " })
        val alphabetLetters: Queue<Char> = ArrayDeque(ALPHABET.toList())

        return buildList {
            repeat(DEFAULT_LETTERS_SIZE) {
                answerLetters.poll()?.let { char ->
                    add(Puzzle.Task.Letter(char = char, id = it))
                } ?: run {
                    add(Puzzle.Task.Letter(char = requireNotNull(alphabetLetters.poll()), id = it))
                }
            }
        }.shuffled()
    }
}