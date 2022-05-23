package andersen.two_clues.data.puzzles.factory

import kotlinx.coroutines.flow.first
import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzles.PuzzleDataStore

class PuzzleCompleteProvider(private val puzzleDataStore: PuzzleDataStore) {
    suspend fun isCompleted(puzzleName: PuzzleName): Boolean {
        return puzzleDataStore.isFinished(puzzleName).first()
    }
}