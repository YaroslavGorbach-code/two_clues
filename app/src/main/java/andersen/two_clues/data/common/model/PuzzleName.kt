package andersen.two_clues.data.common.model

import andersen.two_clues.R

enum class PuzzleName(val resId: Int) {
    LETTER_A(R.string.letter_a_name),
    LETTER_B(R.string.letter_b_name),
    LETTER_C(R.string.letter_c_name),
    LETTER_D(R.string.letter_d_name),
    LETTER_E(R.string.letter_e_name),
    LETTER_F(R.string.letter_f_name),
}

fun PuzzleName.findNext(): PuzzleName? {
    val currentIndex = PuzzleName.values().indexOf(this)
    return PuzzleName.values().getOrNull(currentIndex + 1)
}

fun PuzzleName.isLast(): Boolean {
    return this == PuzzleName.values().last()
}