package io.litecodez.typetrain.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.litecodez.typetrain.*
import io.litecodez.typetrain.components.SimpleAnimator
import io.litecodez.typetrain.objects.AnimationStyle
import io.litecodez.typetrain.objects.TypeTrainTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import typetrain.composeapp.generated.resources.Res
import typetrain.composeapp.generated.resources.confused
import typetrain.composeapp.generated.resources.crying
import typetrain.composeapp.generated.resources.happy

@Composable
fun KeyboardTrainer(modifier: Modifier){
    var level by remember { mutableStateOf(1) }
    var maxTime by remember { mutableStateOf(40) }
    val targetScore by remember { mutableStateOf(10) }
    var currentScore by remember { mutableStateOf(0) }
    var highScore by remember { mutableStateOf(0) }
    var timerTarget by remember { mutableStateOf(1f) }
    var completePercentage by remember { mutableStateOf(0f) }
    var timerColor by remember { mutableStateOf(contentProvider.theme.value.accent.value) }
    var showGame by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var index by remember { mutableStateOf(0) }
    val sentences = remember { listOf(
        SENTENCE_1, SENTENCE_2, SENTENCE_3, SENTENCE_4, SENTENCE_5,
        SENTENCE_6, SENTENCE_7, SENTENCE_8, SENTENCE_9, SENTENCE_10
        )
    }
    var sentence by remember { mutableStateOf(sentences[index]) }
    var input by remember { mutableStateOf("") }
    val timer by animateFloatAsState(
        targetValue = timerTarget,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        ),
        label = "timer"
    )

    LaunchedEffect(timer, currentScore, targetScore) {
        val percentageDone = completePercentage * 100f
        val scorePercentage = (input.length.toFloat() / sentence.length.toFloat()) * 100f

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
            if(input.trim() == sentence && showGame){
                if(index <= 8){
                    currentScore++
                    highScore++
                    index++
                    sentence = sentences[index]
                    level++
                }else{
                    level = 1
                    index = 0
                    sentence = sentences[index]
                    currentScore = 0
                    timerTarget = 1f
                    completePercentage = 0f
                    showGame = false
                }
                currentScore = 0
                timerTarget = 1f
                completePercentage = 0f
                input = ""
                showGame = false
            }else if(input.trim() != sentence && showGame){
                if(index > 0){
                    index--
                    level--
                    sentence = sentences[index]
                }else{
                    level = 1
                    index = 0
                }
                currentScore = 0
                timerTarget = 1f
                completePercentage = 0f
                input = ""
                showGame = false
            }
        }
    }
    Box(modifier = modifier.fillMaxSize().background(color = contentProvider.theme.value.background.value)
        .padding(5.dp)
    ){
       if(showGame) {
            Column(
                modifier = Modifier.fillMaxWidth().align(alignment = Alignment.TopCenter). verticalScroll(scrollState)

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("High Score: $highScore", fontSize = 28.sp, color = contentProvider.theme.value.text.value)
                    Text("Your Level: $level", fontSize = 28.sp, color = contentProvider.theme.value.text.value)
                    Text(
                        "Time given: $maxTime seconds",
                        fontSize = 28.sp,
                        color = contentProvider.theme.value.text.value
                    )
                    Button(
                        onClick = { appNavigator.setViewState(login, updateHistory = false) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = contentProvider.theme.value.accent.value,
                            contentColor = contentProvider.theme.value.text.value
                        )
                    ) {
                        Text("End Game", fontSize = 21.sp)
                    }
                }
            }
            Column(
                modifier = Modifier.padding(5.dp).fillMaxSize().align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Type the sentence below to stay happy!",
                    fontSize = 13.sp,
                    color = contentProvider.theme.value.text.value
                )
                Spacer(modifier = Modifier.height(13.dp))
                Text(
                    sentence,
                    fontSize = 34.sp,
                    color = contentProvider.theme.value.text.value,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(13.dp))
                Image(
                    painter = if (input.isEmpty()) {
                        painterResource(Res.drawable.confused)
                    } else if (sentence.startsWith(input.trim())) {
                        painterResource(Res.drawable.happy)
                    } else {
                        painterResource(Res.drawable.crying)
                    },
                    contentDescription = "Face",
                    modifier = Modifier.fillMaxHeight(0.34f).fillMaxWidth(0.21f).clip(TypeTrainTheme.Shapes.round_8),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(34.dp))
                OutlinedTextField(
                    value = input,
                    onValueChange = {
                        input = it
                        if(input.trim() == sentence){
                            timerTarget = 0f
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = contentProvider.theme.value.text.value,
                        backgroundColor = contentProvider.theme.value.accent.value
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    placeholder = {Text(
                        "Type here!",
                        fontSize = 13.sp,
                        color = contentProvider.theme.value.text.value
                    )}
                )
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
                Text("You are now at Level $level, and you will be given $maxTime seconds to type a sentence exactly as shown. Remember, capitals and punctuation count!",
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