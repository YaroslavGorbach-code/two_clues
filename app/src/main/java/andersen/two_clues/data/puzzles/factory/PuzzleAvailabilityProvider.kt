package andersen.two_clues.data.puzzles.factory

import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzles.PuzzleDataStore
import kotlinx.coroutines.flow.first

class PuzzleAvailabilityProvider(private val puzzleDataStore: PuzzleDataStore) {
    suspend fun isAvailable(puzzleName: PuzzleName): Boolean {
        return when (puzzleName) {
            PuzzleName.LETTER_A -> true
            else -> puzzleDataStore.isAvailable(puzzleName).first()
        }
    }
}