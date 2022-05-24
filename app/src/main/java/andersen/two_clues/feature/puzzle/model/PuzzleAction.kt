package andersen.two_clues.feature.puzzle.model

import android.app.Activity

sealed class PuzzleAction {
    object CheckAnswer : PuzzleAction()
    object RequestShowRewordAd : PuzzleAction()
    class ChoseLetter(val char: Char): PuzzleAction()
    class RemoveLetter(val char: Char?): PuzzleAction()
    class ShowRewordAd(val activity: Activity) : PuzzleAction()
}

