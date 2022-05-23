package andersen.two_clues.data.common.model

import andersen.two_clues.R

enum class PuzzleName(val resId: Int) {
    LETTER_A(R.string.app_name)
}

fun PuzzleName.findNext(): PuzzleName? {
    val currentIndex = PuzzleName.values().indexOf(this)
    return PuzzleName.values().getOrNull(currentIndex + 1)
}

fun PuzzleName.isLast(): Boolean {
    return this == PuzzleName.values().last()
}