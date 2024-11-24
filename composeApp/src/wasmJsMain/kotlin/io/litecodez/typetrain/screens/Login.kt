@file:Suppress("FunctionName")

package io.litecodez.typetrain.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.litecodez.typetrain.appNavigator
import io.litecodez.typetrain.components.SimpleAnimator
import io.litecodez.typetrain.components.ThemeList
import io.litecodez.typetrain.contentProvider
import io.litecodez.typetrain.keyboardTrainer
import io.litecodez.typetrain.mouseTrainer
import io.litecodez.typetrain.objects.AnimationStyle
import io.litecodez.typetrain.objects.TypeTrainTheme
import org.jetbrains.compose.resources.painterResource
import typetrain.composeapp.generated.resources.Res
import typetrain.composeapp.generated.resources.tytr_logo

@Composable
fun Login(modifier: Modifier = Modifier){
    var showThemePicker by remember { mutableStateOf(false) }
    Box(modifier.background(contentProvider.theme.value.background.value).padding(8.dp)){

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.tytr_logo),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxHeight(0.34f).fillMaxWidth(0.21f).clip(TypeTrainTheme.Shapes.round_8),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.26f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Button(
                    onClick = {
                        appNavigator.setViewState(mouseTrainer)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = contentProvider.theme.value.accent.value,
                        contentColor = contentProvider.theme.value.text.value
                    ),
                    modifier = Modifier.fillMaxWidth(0.34f).fillMaxHeight(0.13f).weight(1f)
                ) {
                    Text("Mouse Trainer", fontSize = 25.sp)
                }
                Spacer(modifier = Modifier.height(21.dp).weight(0.25f))
                Button(
                    onClick = {
                        appNavigator.setViewState(keyboardTrainer)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = contentProvider.theme.value.accent.value,
                        contentColor = contentProvider.theme.value.text.value
                    ),
                    modifier = Modifier.fillMaxWidth(0.34f).fillMaxHeight(0.13f).weight(1f)
                ) {
                    Text("Typing Trainer", fontSize = 25.sp)
                }
            }
        }

        Button(
            onClick = {
                showThemePicker = true
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = contentProvider.theme.value.accent.value,
                contentColor = contentProvider.theme.value.text.value
            ),
            modifier = Modifier.align(Alignment.TopEnd)
        ){
            Text("Change Theme")
        }
        SimpleAnimator(
            modifier = Modifier.align(Alignment.TopEnd),
            isVisible = showThemePicker,
            style = AnimationStyle.DOWN
        ) {
            ThemeList(
                modifier = Modifier.wrapContentWidth().wrapContentHeight().align(Alignment.TopEnd)
            ) { showThemePicker = it }
        }
    }
}