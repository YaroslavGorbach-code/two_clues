package andersen.two_clues.data.common

import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzle.factory.PuzzleFactory
import andersen.two_clues.data.puzzle.model.Puzzle
import andersen.two_clues.data.puzzles.PuzzleDataStore
import andersen.two_clues.data.puzzles.factory.PuzzlesFactory
import andersen.two_clues.data.puzzles.model.PuzzleItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PuzzleRepoImp @Inject constructor(
    private val puzzleFactory: PuzzleFactory,
    private val puzzlesFactory: PuzzlesFactory,
    private val puzzleDataStore: PuzzleDataStore
) : PuzzleRepo {

    override fun getPuzzle(puzzleName: PuzzleName): Flow<Puzzle> {
        return flowOf(puzzleFactory.create(puzzleName))
    }

    override suspend fun getPuzzleItems(): Flow<List<PuzzleItem>> {
        return flowOf(puzzlesFactory.create())
    }

    override suspend fun makeAvailable(name: PuzzleName) {
        puzzleDataStore.makeAvailable(name)
    }

    override suspend fun finishPuzzle(name: PuzzleName) {
        puzzleDataStore.finishPuzzle(name)
    }
}