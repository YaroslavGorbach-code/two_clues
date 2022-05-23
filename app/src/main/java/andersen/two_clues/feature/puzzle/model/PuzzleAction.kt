package andersen.two_clues.feature.puzzle.model

import android.app.Activity

sealed class PuzzleAction {
    object CheckAnswer : PuzzleAction()
    object RequestShowRewordAd : PuzzleAction()
    class ShowRewordAd(val activity: Activity, ) : PuzzleAction()
}

