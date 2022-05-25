package andersen.two_clues.feature.puzzle.model

import andersen.two_clues.data.puzzle.model.Puzzle
import android.app.Activity

sealed class PuzzleAction {
    object CheckAnswer : PuzzleAction()
    object NextTask: PuzzleAction()
    object RevealLetter: PuzzleAction()
    object RequestShowRewordAd : PuzzleAction()
    class ChoseLetter(val letter: Puzzle.Task.Letter): PuzzleAction()
    class RemoveLetter(val letter: Puzzle.Task.Letter?): PuzzleAction()
    class ShowRewordAd(val activity: Activity) : PuzzleAction()
}

