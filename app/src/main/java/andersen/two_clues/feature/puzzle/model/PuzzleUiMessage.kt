package andersen.two_clues.feature.puzzle.model

sealed class PuzzleUiMessage {
    object ShowWinDialog: PuzzleUiMessage()
    object ShowRewardAd : PuzzleUiMessage()
    object ShowAdWarningDialog : PuzzleUiMessage()
}