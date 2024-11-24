package io.litecodez.typetrain.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.litecodez.typetrain.appNavigator
import io.litecodez.typetrain.components.BoxWithRandomElement
import io.litecodez.typetrain.components.SimpleAnimator
import io.litecodez.typetrain.contentProvider
import io.litecodez.typetrain.login
import io.litecodez.typetrain.objects.AnimationStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MouseTrainer(modifier: Modifier = Modifier){

    var level by remember { mutableStateOf(1) }
    var maxTime by remember { mutableStateOf(30) }
    val targetScore by remember { mutableStateOf(10) }
    var currentScore by remember { mutableStateOf(0) }
    var highScore by remember { mutableStateOf(0) }
    var timerTarget by remember { mutableStateOf(1f) }
    var completePercentage by remember { mutableStateOf(0f) }
    var timerColor by remember { mutableStateOf(contentProvider.theme.value.accent.value) }
    var showGame by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val timer by animateFloatAsState(
        targetValue = timerTarget,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        ),
        label = "timer"
    )
    LaunchedEffect(showError){
        if (showError){
            delay(500)
            showError = false
        }
    }
    LaunchedEffect(timer, currentScore, targetScore) {
        val percentageDone = completePercentage * 100f
        val scorePercentage = (currentScore.toFloat() / targetScore.toFloat()) * 100f

        timerColor = when {
            percentageDone > scorePercentage -> Color.Red
            percentageDone < scorePercentage -> Color.Green
            else -> contentProvider.theme.value.accent.value
        }
    }

    LaunchedEffect(level, maxTime, showGame) {
        timerTarget = 1f
        completePercentage = 0f
        val subtract = 1f / maxTime
        if(showGame) {
            launch {
                repeat(maxTime+1) {
                    delay(1000)
                    timerTarget -= subtract
                    completePercentage += subtract
                }
            }
        }
    }
    LaunchedEffect(currentScore){
        if(currentScore > highScore){
            highScore = currentScore
        }
    }
    LaunchedEffect(timer){
        if(timer <= 0){
            if(currentScore >= targetScore && showGame){
                if(maxTime > 5){
                    maxTime-=5
                    level++
                }
                currentScore = 0
                timerTarget = 1f
                completePercentage = 0f
                showGame = false
            }else if(currentScore < targetScore && showGame){
                maxTime = 30
                level = 1
                currentScore = 0
                timerTarget = 1f
                completePercentage = 0f
                showGame = false
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()){
        if(showGame){
            Column(
                modifier = Modifier.fillMaxSize().background(color = contentProvider.theme.value.background.value)
                    .padding(5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    LinearProgressIndicator(
                        progress = timer,
                        modifier = Modifier.fillMaxWidth().height(13.dp),
                        strokeCap = StrokeCap.Round,
                        color = timerColor,
                        backgroundColor = timerColor.copy(alpha = 0.3f)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Text("High Score: $highScore", fontSize = 28.sp, color = contentProvider.theme.value.text.value)
                    Text("Your Score: $currentScore", fontSize = 28.sp, color = contentProvider.theme.value.text.value)
                    Text("Your Level: $level", fontSize = 28.sp, color = contentProvider.theme.value.text.value)
                    Text("Time given: $maxTime seconds", fontSize = 28.sp, color = contentProvider.theme.value.text.value)
                    Button(onClick = { appNavigator.setViewState(login, updateHistory = false)}, colors = ButtonDefaults.buttonColors(
                        backgroundColor = contentProvider.theme.value.accent.value,
                        contentColor = contentProvider.theme.value.text.value
                    )){
                        Text("End Game", fontSize = 21.sp)
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    SimpleAnimator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isVisible = showError,
                        style = AnimationStyle.DOWN
                    ) {
                        Text("!!", fontSize = 84.sp, modifier = Modifier.align(Alignment.TopCenter), color = Color.Red)
                    }
                    BoxWithRandomElement(
                        modifier = Modifier.fillMaxWidth(0.95f).fillMaxHeight(0.9f).align(Alignment.BottomCenter)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                if(timer > 0f) {
                                    showError = true
                                    currentScore -= 1
                                }
                            },
                        padding = 13
                    ) { position, updatePosition ->
                        val currentPosition by position
                        Box(
                            modifier = Modifier
                                .offset { currentPosition }
                                .size(34.dp)
                                .background(Color.Blue, shape = CircleShape)
                                .border(shape = CircleShape, width = 2.dp, color = timerColor.copy(alpha = 0.0f))
                                .clip(CircleShape)
                                .clickable {
                                    if(timer > 0f) {
                                        updatePosition()
                                        currentScore += 1
                                    }
                                }
                        )
                    }
                }

            }
        }
        SimpleAnimator(
            style = AnimationStyle.SCALE_IN_CENTER,
            modifier = Modifier.align(Alignment.Center),
            isVisible = !showGame
        ) {
            Column(
                modifier = Modifier.fillMaxSize().align(Alignment.Center).background(color = contentProvider.theme.value.background.value),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("You are now at Level $level, and you will be given $maxTime seconds to achieve a total of $targetScore points or more. Use the mouse to click the circle as it moves about the screen. Good Luck!",
                    fontSize = 21.sp, color = contentProvider.theme.value.text.value, textAlign = TextAlign.Center)
                Button(onClick = {showGame = true}, colors = ButtonDefaults.buttonColors(
                    backgroundColor = contentProvider.theme.value.accent.value,
                    contentColor = contentProvider.theme.value.text.value
                )){
                    Text("START", fontSize = 21.sp)
                }
            }
        }
    }

}