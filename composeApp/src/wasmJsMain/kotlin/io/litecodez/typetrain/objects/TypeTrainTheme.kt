package io.litecodez.typetrain.objects

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import io.litecodez.typetrain.models.ThemeModel

object TypeTrainTheme {
    object ColourScheme{

        // Light theme
        val light: ThemeModel = ThemeModel()

        // Dark theme
        val dark: ThemeModel = ThemeModel(
            background = mutableStateOf(Color(0xFF121212)),
            accent = mutableStateOf(Color(0xff0008af)),
            text = mutableStateOf(Color(0xFFFFFFFF))
        )

        // High contrast theme
        val highContrast: ThemeModel = ThemeModel(
            background = mutableStateOf(Color(0xFF000000)),
            accent = mutableStateOf(Color(0xffe91e63)),
            text = mutableStateOf(Color(0xFFFFEB3B))
        )

        // Custom theme
        val custom: ThemeModel = ThemeModel(
            background = mutableStateOf(Color(0xFF212121)),
            accent = mutableStateOf(Color(0xFF8E24AA)),
            text = mutableStateOf(Color(0xFFB0BEC5))
        )
        val themeMap = mapOf("Light" to light, "Dark" to dark, "High Contrast" to highContrast, "Custom" to custom)
    }

    object Shapes{
        val round_3 = RoundedCornerShape(3)
        val round_5 = RoundedCornerShape(5)
        val round_8 = RoundedCornerShape(8)
        val round_13 = RoundedCornerShape(13)
        val round_21 = RoundedCornerShape(21)
        val round_34 = RoundedCornerShape(34)
    }
}