package andersen.two_clues.feature.puzzles.model

import andersen.two_clues.data.common.model.PuzzleName
import android.app.Activity

sealed class PuzzlesAction {
    class ShowPuzzleUnAvailableDialog(val name: PuzzleName) : PuzzlesAction()
    object LoadPuzzle : PuzzlesAction()
    data class ShowRewordAd(val activity: Activity, val puzzleName: PuzzleName) : PuzzlesAction()
    data class RequestShowRewordAd(val puzzleName: PuzzleName) : PuzzlesAction()
}