package andersen.two_clues.data.common

import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzle.model.Puzzle
import andersen.two_clues.data.puzzles.model.PuzzleItem
import kotlinx.coroutines.flow.Flow

interface PuzzleRepo {
    fun getPuzzle(puzzleName: PuzzleName): Flow<Puzzle>
    suspend fun getPuzzleItems(): Flow<List<PuzzleItem>>
    suspend fun makeAvailable(name: PuzzleName)
    suspend fun finishPuzzle(name: PuzzleName)
}