package andersen.two_clues.data.common.model

import andersen.two_clues.R

enum class PuzzleName(val resId: Int) {
    LETTER_A(R.string.letter_a_name),
    LETTER_B(R.string.letter_b_name),
    LETTER_C(R.string.letter_c_name),
    LETTER_D(R.string.letter_d_name),
    LETTER_E(R.string.letter_e_name),
    LETTER_F(R.string.letter_f_name),
    LETTER_G(R.string.letter_g_name),
    LETTER_H(R.string.letter_h_name),
    LETTER_L(R.string.letter_l_name),
    LETTER_M(R.string.letter_m_name),
    LETTER_N(R.string.letter_n_name),
    LETTER_O(R.string.letter_o_name),
    LETTER_P(R.string.letter_p_name),
    LETTER_R(R.string.letter_r_name),
    LETTER_S(R.string.letter_s_name),
    LETTER_T(R.string.letter_t_name),
}

fun PuzzleName.findNext(): PuzzleName? {
    val currentIndex = PuzzleName.values().indexOf(this)
    return PuzzleName.values().getOrNull(currentIndex + 1)
}

fun PuzzleName.isLast(): Boolean {
    return this == PuzzleName.values().last()
}