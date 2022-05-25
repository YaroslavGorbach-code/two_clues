package andersen.two_clues.feature.puzzle.ui

import andersen.two_clues.data.puzzle.model.Puzzle
import andersen.two_clues.feature.common.ui.theme.getOnBackgroundColor
import andersen.two_clues.feature.common.ui.theme.getOnBackgroundColorLight
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun VariantCell(letter: Puzzle.Task.Letter, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .padding(1.dp)
            .background(
                color = if (letter.isUsed) getOnBackgroundColorLight() else getOnBackgroundColor(),
                shape = RoundedCornerShape(3)
            )
            .clickable {
                if (letter.isUsed.not()) onClick()
            }


    ) {
        Text(
            text = letter.char.toString().uppercase(),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.body2
        )
    }
}