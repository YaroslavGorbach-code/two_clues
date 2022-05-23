package andersen.two_clues.data.puzzle.factory

import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzle.model.Puzzle

class PuzzleFactory(private val tasksFactory: TasksFactory) {
    fun create(name: PuzzleName): Puzzle {
        return Puzzle(
            name = name,
            task = tasksFactory.create(name)
        )
    }
}