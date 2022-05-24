package andersen.two_clues.feature.puzzle.ui

import andersen.two_clues.feature.common.ui.theme.getOnBackgroundColor
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun AnswerCell(letter: Char?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .background(color = getOnBackgroundColor())
            .clickable { onClick() }

    ) {
        letter?.uppercase()?.let { letter ->
            Text(
                text = letter,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.body2
            )
        }

        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            drawRect(
                color = Color.DarkGray, style = Stroke(
                    width = 1f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            )
        })
    }
}