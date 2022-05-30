package andersen.two_clues.data.puzzles.factory

import andersen.two_clues.R
import andersen.two_clues.data.common.model.PuzzleName
import android.content.Context

class PuzzleDescriptionProvider(private val context: Context) {

    fun provide(puzzleName: PuzzleName): String {
        return when (puzzleName) {
            PuzzleName.LETTER_A -> context.resources.getString(R.string.letter_a_description)
            PuzzleName.LETTER_B -> context.resources.getString(R.string.letter_b_description)
            PuzzleName.LETTER_C -> context.resources.getString(R.string.letter_c_description)
            PuzzleName.LETTER_D -> context.resources.getString(R.string.letter_d_description)
            PuzzleName.LETTER_E -> context.resources.getString(R.string.letter_e_description)
            PuzzleName.LETTER_F -> context.resources.getString(R.string.letter_f_description)
            PuzzleName.LETTER_G -> context.resources.getString(R.string.letter_g_description)
            PuzzleName.LETTER_H -> context.resources.getString(R.string.letter_h_description)
            PuzzleName.LETTER_L -> context.resources.getString(R.string.letter_l_description)
            PuzzleName.LETTER_M -> context.resources.getString(R.string.letter_m_description)
            PuzzleName.LETTER_N -> context.resources.getString(R.string.letter_n_description)
            PuzzleName.LETTER_O -> context.resources.getString(R.string.letter_o_description)
            PuzzleName.LETTER_P -> context.resources.getString(R.string.letter_p_description)
            PuzzleName.LETTER_R -> context.resources.getString(R.string.letter_r_description)
            PuzzleName.LETTER_S -> context.resources.getString(R.string.letter_s_description)
        }
    }
}