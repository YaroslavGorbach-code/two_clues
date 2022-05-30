package andersen.two_clues.feature.puzzles.model

import andersen.two_clues.data.common.model.PuzzleName

sealed class PuzzlesUiMessage {
    class ShowPuzzleUnAvailableDialog(val name: PuzzleName): PuzzlesUiMessage()
    data class ShowRewardAd(val puzzleName: PuzzleName) : PuzzlesUiMessage()
}