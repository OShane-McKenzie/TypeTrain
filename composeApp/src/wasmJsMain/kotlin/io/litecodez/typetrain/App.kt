package io.litecodez.typetrain

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.litecodez.typetrain.objects.AppNavigator
import io.litecodez.typetrain.screens.Login
import org.jetbrains.compose.resources.painterResource

import typetrain.composeapp.generated.resources.Res
import typetrain.composeapp.generated.resources.compose_multiplatform
val appNavigator = AppNavigator(initialScreen = login)
@Composable
fun App() {
    MaterialTheme {
        Views(modifier = Modifier.fillMaxSize())
    }
}