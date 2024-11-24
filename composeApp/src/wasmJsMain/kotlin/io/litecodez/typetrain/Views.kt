package io.litecodez.typetrain

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.litecodez.typetrain.components.SimpleAnimator
import io.litecodez.typetrain.screens.KeyboardTrainer
import io.litecodez.typetrain.screens.Login
import io.litecodez.typetrain.screens.MouseTrainer

@Composable
fun Views(modifier: Modifier = Modifier){
    Box(modifier.fillMaxSize()){
        when(appNavigator.getView()){
            login -> {
                SimpleAnimator {
                    Login(modifier = Modifier.fillMaxSize())
                }
            }
            mouseTrainer -> {
                SimpleAnimator {
                    MouseTrainer(modifier = Modifier.fillMaxSize())
                }
            }
            keyboardTrainer -> {
                SimpleAnimator { KeyboardTrainer(modifier = Modifier.fillMaxSize()) }
            }
        }
    }
}