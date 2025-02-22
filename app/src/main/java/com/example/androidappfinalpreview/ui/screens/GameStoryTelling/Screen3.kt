package com.example.androidappfinalpreview.ui.screens.GameStoryTelling

import android.media.MediaPlayer
//import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidappfinalpreview.DataModel.Difficulty
import com.example.androidappfinalpreview.DataModel.GenStory
import com.example.androidappfinalpreview.DataModel.Scores
import com.example.androidappfinalpreview.R
import com.example.androidappfinalpreview.TTS.TTSProvider
import com.example.androidappfinalpreview.ui.theme.onSecondaryColor
import com.example.androidappfinalpreview.ui.theme.onPrimaryColor
import com.example.androidappfinalpreview.ui.theme.primaryColor
import kotlinx.coroutines.delay


@Composable
fun Screen3(navController: NavController) {
    // Context for playing sound
    val context = LocalContext.current
    var showCountdown by remember { mutableStateOf(true) }
    var countdownValue by remember { mutableStateOf(3) }


    LaunchedEffect(Unit) {
        // Countdown loop with sound
        while (countdownValue > 0) {
            // Play a sound for each countdown step
            MediaPlayer.create(context, R.raw.count_tone).apply {
                start()
                setOnCompletionListener {
                    release() // Release resources once done
                }
            }


            delay(1000L) // 1-second delay
            countdownValue--
        }
        // Once countdown is finished, show the main QuestionScreen
        showCountdown = false
    }

    DisposableEffect(Unit){
        onDispose {
            TTSProvider.get()?.stopTTS()
        }
    }

    if (showCountdown) {
        // Countdown screen
        Box(
            modifier = Modifier.fillMaxSize()
                .background(primaryColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = countdownValue.toString(),
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    } else {
        // Display the original QuestionScreen after countdown
        QuestionScreen(navController)
    }
}


@Composable
fun QuestionScreen(navController: NavController) {
    // Access the current MaterialTheme's color scheme
    val colors = MaterialTheme.colorScheme


    // Context and haptic feedback
    val context = LocalContext.current
    val hapticFeedback = LocalHapticFeedback.current


    // Variable to store the number of correct answers
    var correctAnswered by remember { mutableStateOf(0) }
    var questions = GenStory.getEasyQuestions()

    if(Difficulty.getDifficulty() == "Medium"){
        questions = GenStory.getMediumQuestions()
    }
    else if(Difficulty.getDifficulty() == "Hard"){
        questions = GenStory.getHardQuestions()
    }


    // State variables
    var currentQuestionIndex by remember { mutableStateOf(0) }
    val currentQuestion = questions[currentQuestionIndex]


    // List of elements to cycle through (question and options)
    val elements = listOf(currentQuestion.text) + currentQuestion.options


    // State variable for the current element index
    var currentElementIndex by remember { mutableStateOf(0) }


    LaunchedEffect(Unit){
        TTSProvider.get()?.speak(questions[currentQuestionIndex].text)
    }


    // Effect to reset currentElementIndex when question changes
    LaunchedEffect(currentQuestionIndex) {
        currentElementIndex = 0
    }


    // Function to handle option selection
    fun selectOption(optionIndex: Int) {
        if (optionIndex in currentQuestion.options.indices) {
            if (optionIndex == currentQuestion.correctAnswerIndex) {
                correctAnswered++
//                Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()


                // Play correct answer sound
                MediaPlayer.create(context, R.raw.correct_answer).apply {
                    start()
                    setOnCompletionListener {
                        release() // Release resources once done
                    }
                }
            } else {
//                Toast.makeText(context, "Incorrect!", Toast.LENGTH_SHORT).show()
                MediaPlayer.create(context, R.raw.wrong_answer).apply {
                    start()
                    setOnCompletionListener {
                        release() // Release resources once done
                    }
                }
            }
            // Move to the next question or reset if at the end
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                TTSProvider.get()?.speak(questions[currentQuestionIndex].text)
            } else {
                // Quiz completed
//                Toast.makeText(
//                    context,
//                    "Quiz Completed! Correct Answers: $correctAnswered",
//                    Toast.LENGTH_LONG
//                ).show()
                Scores.setScoresGame1(correctAnswered)
                navController.navigate("Screen4")
            }
        }
    }


    // UI
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top 75% section for question content and options
            Column(
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Elevated Card only around the question
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = currentQuestion.text,
                            fontSize = 22.sp,
                            color = colors.onSurface,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))


                // Display options below the Card with shadows
                currentQuestion.options.forEachIndexed { index, option ->
                    Button(
                        onClick = {
                            selectOption(index)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(12.dp),
                                clip = false
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = onPrimaryColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Text(
                            text = option,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }


            // Bottom 25% section for navigation accessibility
            // Wrapping with 'key' to force recomposition when question changes
            key(currentQuestionIndex) {
                Box(
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { offset ->
                                    val width = size.width
                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                    if (offset.x < width / 2) {
                                        // Left side tapped - move to previous element
                                        currentElementIndex =
                                            (currentElementIndex - 1 + elements.size) % elements.size
                                    } else {
                                        // Right side tapped - move to next element
                                        currentElementIndex =
                                            (currentElementIndex + 1) % elements.size
                                    }
                                    // Show a toast message with the content of the current element
                                    val currentElement = elements[currentElementIndex]
//                                    Toast
//                                        .makeText(
//                                            context,
//                                            currentElement,
//                                            Toast.LENGTH_SHORT
//                                        )
//                                        .show()
                                    TTSProvider
                                        .get()
                                        ?.speak(elements[currentElementIndex])
                                },
                                onDoubleTap = {
                                    val currentElement = elements[currentElementIndex]
//                                    if (currentElementIndex == 0) {
//                                        // Current element is the question text
//                                        Toast.makeText(
//                                            context,
//                                            "Question: $currentElement",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                        TTSProvider.get()?.speak(currentElement)
//                                    } else {
                                    // Current element is an option
                                    // Adjust optionIndex by subtracting 1 since options start at index 1
                                    selectOption(currentElementIndex - 1)
//                                        TTSProvider.get()?.speak("$currentElement is selected")
                                }
                            )
                        },
                )
            }
        }
    }
}





//// Preview function
//@Composable
//@Preview(showBackground = true)
//fun PreviewMainScreen() {
//    MainScreen()
//}
