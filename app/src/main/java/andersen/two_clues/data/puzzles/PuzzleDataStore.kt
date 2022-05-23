package andersen.two_clues.data.puzzles

import kotlinx.coroutines.flow.Flow
import andersen.two_clues.data.common.model.PuzzleName

interface PuzzleDataStore {
    suspend fun makeAvailable(name: PuzzleName)
    fun isAvailable(name: PuzzleName): Flow<Boolean>
    suspend fun finishPuzzle(name: PuzzleName)
    fun isFinished(name: PuzzleName): Flow<Boolean>
}