package com.example.jetweatherforecast.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
//    primaryContainer = PrimaryContainerDark,
//    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
//    secondaryContainer = SecondaryContainerDark,
//    onSecondaryContainer = OnSecondaryContainerDark,
//    tertiary = TertiaryDark,
//    onTertiary = OnTertiaryDark,
//    tertiaryContainer = TertiaryContainerDark,
//    onTertiaryContainer = OnTertiaryContainerDark,
    error = ErrorDark,
    onError = OnErrorDark,
//    errorContainer = ErrorContainerDark,
//    onErrorContainer = OnErrorContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark1,
    surface = SurfaceDark,
//    outline = OutlineDark,
//    surfaceVariant = SurfaceVariantDark,
//    onSurfaceVariant = OnSurfaceVariantDark,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
//    primaryContainer = PrimaryContainerLight,
//    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
//    secondaryContainer = SecondaryContainerLight,
//    onSecondaryContainer = OnSecondaryContainerLight,
//    tertiary = TertiaryLight,
//    onTertiary = OnTertiaryLight,
//    tertiaryContainer = TertiaryContainerLight,
//    onTertiaryContainer = OnTertiaryContainerLight,
    error = ErrorLight,
    onError = OnErrorLight,
//    errorContainer = ErrorContainerLight,
//    onErrorContainer = OnErrorContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
//    outline = OutlineLight,
//    surfaceVariant = SurfaceVariantLight,
//    onSurfaceVariant = OnSurfaceVariantLight,
)

@Composable
fun JetWeatherForecastTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        DarkColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
    // Optional, this part helps you set the statusbar color
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.background.toArgb()

            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }
}