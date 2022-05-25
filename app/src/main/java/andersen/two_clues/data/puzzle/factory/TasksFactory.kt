package andersen.two_clues.data.puzzle.factory

import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzle.mapper.AnswerToLettersMapper
import andersen.two_clues.data.puzzle.model.Puzzle
import andersen.two_clues.data.puzzle.provider.TaskAnswersProvider
import andersen.two_clues.data.puzzle.provider.TaskCluesProvider

class TasksFactory(
    private val puzzleAnswerToLettersMapper: AnswerToLettersMapper,
    private val taskCluesProvider: TaskCluesProvider,
    private val puzzleTaskAnswersProvider: TaskAnswersProvider
) {

    fun create(name: PuzzleName): List<Puzzle.Task> {
        return taskCluesProvider.provider(name)
            .zip(puzzleTaskAnswersProvider.provide(name)).mapIndexed { index, clueAndAnswer ->
                Puzzle.Task(
                    clues = clueAndAnswer.first,
                    correctAnswer = clueAndAnswer.second.map { it.toList() },
                    correctAnswerString = clueAndAnswer.second.joinToString(separator = ""),
                    letters = puzzleAnswerToLettersMapper.map(clueAndAnswer.second),
                    orderNumber = index.inc()
                )
            }
    }
}