package io.litecodez.typetrain.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

data class ThemeModel(
    val background:MutableState<Color> = mutableStateOf(Color(0xFFFFFFFF)),
    val accent:MutableState<Color> = mutableStateOf(Color(0xff02d1fb)),
    val text:MutableState<Color> = mutableStateOf(Color(0xff000000))
)
