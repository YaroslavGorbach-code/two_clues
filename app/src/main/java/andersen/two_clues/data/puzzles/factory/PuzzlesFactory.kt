package andersen.two_clues.data.puzzles.factory

import andersen.two_clues.data.common.model.PuzzleName
import andersen.two_clues.data.puzzles.model.PuzzleItem

class PuzzlesFactory(
    private val puzzleDescriptionProvider: PuzzleDescriptionProvider,
    private val puzzleAvailabilityProvider: PuzzleAvailabilityProvider,
    private val puzzleCompleteProvider: PuzzleCompleteProvider
) {
    private val names: List<PuzzleName> = PuzzleName.values().toList()

    suspend fun create(): List<PuzzleItem> {
        return names.map { name ->
            PuzzleItem(
                name = name,
                description = puzzleDescriptionProvider.provide(name),
                isAvailable = puzzleAvailabilityProvider.isAvailable(name),
                isCompleted = puzzleCompleteProvider.isCompleted(name)
            )
        }
    }
}