package andersen.two_clues.data.puzzles.factory

import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzles.PuzzleDataStore

class PuzzleAvailabilityProvider(private val puzzleDataStore: PuzzleDataStore) {
    suspend fun isAvailable(puzzleName: PuzzleName): Boolean {
        return when (puzzleName) {
            else -> true
//            else -> puzzleDataStore.isAvailable(puzzleName).first()
        }
    }
}