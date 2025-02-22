package com.example.androidappfinalpreview.ui.screens.GameWordAssociation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.RecognizerIntent
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.androidappfinalpreview.DataModel.Difficulty
import com.example.androidappfinalpreview.DataModel.Scores
import com.example.androidappfinalpreview.R
import com.example.androidappfinalpreview.TTS.TTSProvider
import com.example.androidappfinalpreview.ui.theme.primaryColor
import com.example.androidappfinalpreview.modelAI.GenerativeModelManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun Game2Screen2(navController: NavController) {
    val context = LocalContext.current
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    var cycleIndex by remember { mutableStateOf(0) }
    var userResponse by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf("apple")) } // Start with a single word "apple"
    var messageHistory by remember { mutableStateOf(listOf<String>()) }
    val listState = rememberLazyListState()
    var isTyping by remember { mutableStateOf(false) }


    DisposableEffect(Unit){
        onDispose {
            TTSProvider.get()?.stopTTS()
        }
    }

    // Timer state
    var time = 120
    when(Difficulty.getDifficulty()){
        "Easy"->{
            time = 120
        }
        "Medium"->{
            time = 90
        }
        "Hard"->{
            time = 60
        }
    }
    var timeLeft by remember { mutableStateOf(time) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }
    var timerRunning by remember { mutableStateOf(false) }

    // Timer logic
    fun startTimer() {
        timer?.cancel() // Cancel any existing timer
        timer = object : CountDownTimer(timeLeft * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                timerRunning = false
                navController.navigate("Game2Screen3")
            }
        }.start()
        timerRunning = true
    }

    LaunchedEffect(Unit) {
        if (!timerRunning) {
            startTimer() // Start the timer when the Composable is first launched
        }
    }

    // Request permissions if necessary
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as ComponentActivity,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                1
            )
        }
    }

    // Show app's message before recording
    LaunchedEffect(cycleIndex) {
        if (cycleIndex < messages.size) {
            if (cycleIndex > 0) {
                isTyping = true
                delay(1500) // Simulate typing delay
                isTyping = false
            }
            if (messageHistory.lastOrNull() != "App: ${messages[cycleIndex]}") {
                messageHistory = messageHistory + "App: ${messages[cycleIndex]}"
            }
        }
    }

    // Auto-scroll to the bottom when a new message is added
    LaunchedEffect(messageHistory) {
        listState.animateScrollToItem(messageHistory.size)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Column for chat messages
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                items(messageHistory) { message ->
                    ChatBubble(message = message)
                }
                if (isTyping) {
                    item {
                        TypingIndicator()
                    }
                }
            }
        }

        // Microphone button and timer display
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FloatingActionButton(
                onClick = {
                    if (cycleIndex < messages.size && timerRunning) {
                        startListening(speechRecognizer) { result ->
                            if (!result.startsWith("Error")) {
                                userResponse = result
                                messageHistory = messageHistory + "You: $userResponse"

                                // Trigger generation logic
                                isTyping = true
                                val curr = messages[cycleIndex]
                                generateNextMessage(userResponse,curr,context) { newMessage ->
                                    isTyping = false
                                    messages = messages + newMessage
                                    cycleIndex++
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.size(80.dp),
                containerColor = primaryColor,
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.microphone),
                    contentDescription = "Record Button",
                    modifier = Modifier.size(40.dp)
                )
            }

            // Timer display below the microphone with increased padding
            Text(
                text = "$timeLeft",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 32.dp) // Increased padding for more spacing below the microphone
            )
        }
    }
}

@Composable
fun ChatBubble(message: String) {
    val isUserMessage = message.startsWith("You:")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isUserMessage) Color(0xff5a5b9f) else Color(0xffc74375),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.removePrefix("You:").removePrefix("App:"),
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun TypingIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Surface(
            color = Color(0xffc74375),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            modifier = Modifier.padding(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(3) { index ->
                    AnimatedDot(index)
                }
            }
        }
    }
}

@Composable
fun AnimatedDot(index: Int) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearOutSlowInEasing, delayMillis = index * 150),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier
            .size(8.dp)
            .scale(scale)
            .background(color = Color.White, shape = androidx.compose.foundation.shape.CircleShape)
    )
}

private fun startListening(
    speechRecognizer: SpeechRecognizer,
    onResult: (String) -> Unit
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
    }

    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onError(error: Int) {
            onResult("Error recognizing speech: $error")
        }
        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            onResult(matches?.get(0) ?: "No results")
        }
        override fun onPartialResults(partialResults: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    })

    speechRecognizer.startListening(intent)
}

private fun generateNextMessage(input: String, previnput:String, context: Context, onMessageGenerated: (String) -> Unit){
    // Simulating a network/model call delay for message generation
    // Replace with actual call to ModelManager.generate(input)
    kotlinx.coroutines.GlobalScope.launch {
//        delay(2000) // Simulated delay
        val score = GenerativeModelManager.generateResponse("Give me only a value between 1 and 100 which defines the contextual similarity between $input and "+ previnput+". The response should ony be a number.")
        Log.i("Mohit Varma","The words are $input and $previnput. The score is $score")
        if (score != null) {
            if (score.toInt()>50) {
                MediaPlayer.create(context, R.raw.correct_answer).apply {
                    start()
                    setOnCompletionListener {
                        release() // Release resources once done
                    }
                }
                Scores.setScoresGame2(Scores.getScoresGame2()+1)
            }
            else{
                MediaPlayer.create(context, R.raw.wrong_answer).apply {
                    start()
                    setOnCompletionListener {
                        release() // Release resources once done
                    }
                }
            }
        }
        val newMessage = GenerativeModelManager.generateResponse("Generate a single word contexually related to $input. Do not consider proper nouns.The response should only be a single word")
        if (newMessage != null) {
            onMessageGenerated(newMessage)
        }
    }
}

//@Preview
//@Composable
//fun myPreview123() {
//    SpeechToTextScreen()
//}
