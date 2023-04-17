package com.example.filmbookdiary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = darkPrimaryColor,
    primaryVariant = accentColor,
    secondary = primaryColor
)

private val LightColorPalette = lightColors(
    primary = primaryColor,
    primaryVariant = accentColor,
    secondary = secondaryColor,
    background = backgroundColor,
    surface = Color.White,
    onPrimary = textColor,
    onSecondary = textColor,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun FilmBookDiaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}