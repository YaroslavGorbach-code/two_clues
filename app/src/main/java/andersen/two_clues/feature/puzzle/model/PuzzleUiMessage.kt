package andersen.two_clues.feature.puzzle.model

sealed class PuzzleUiMessage {
    object ShowWinDialog: PuzzleUiMessage()
    object ShowPuzzleErrorDialog: PuzzleUiMessage()
    object ShowRewardAd : PuzzleUiMessage()
}