@file:Suppress("FunctionName")

package io.litecodez.typetrain.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.litecodez.typetrain.contentProvider
import io.litecodez.typetrain.objects.TypeTrainTheme

@Composable
fun ThemeList(modifier: Modifier = Modifier, onThemeChanged:(Boolean)->Unit = {}){
    val scrollState = rememberScrollState()
    val themes = remember { TypeTrainTheme.ColourScheme.themeMap.keys.toList() }
    Column(
        modifier
            .verticalScroll(scrollState)
            .background(color = contentProvider.theme.value.accent.value, TypeTrainTheme.Shapes.round_5),
        verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        themes.forEach {
            TextButton(onClick = {
                contentProvider.theme.value = TypeTrainTheme.ColourScheme.themeMap[it]!!
                onThemeChanged.invoke(false)
            }
            ){
                Text(it, color = contentProvider.theme.value.text.value, fontSize = 21.sp)
            }
        }
    }
}