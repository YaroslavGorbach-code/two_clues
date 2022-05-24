package andersen.two_clues.feature.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Blue700 = Color(0xFF6E58F3)
val Blue400 = Color(0xFF9181F2)
val Blue200 = Color(0xFFA396F1)
val Pink200 = Color(0xFFE040FB)
val LightBlue = Color(0xFF304FFE)

@Composable
fun getOnBackgroundColorLight(isDarkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (isDarkTheme) {
        Color(0xFF555555)
    } else {
        Color(0xFFF3F3F3)
    }
}

@Composable
fun getOnBackgroundColor(isDarkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (isDarkTheme) {
        Color(0xFF303030)
    } else {
        Color(0xFFECECEC)
    }
}

@Composable
fun getOnBackgroundHinted(isDarkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (isDarkTheme) {
        Color(0xFF222222)
    } else {
        Color(0xFFD6D6D6)

    }
}