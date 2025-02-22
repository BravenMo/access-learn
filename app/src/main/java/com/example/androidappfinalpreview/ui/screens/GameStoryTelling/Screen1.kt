package com.example.androidappfinalpreview.ui.screens.GameStoryTelling

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidappfinalpreview.DataModel.Difficulty
import com.example.androidappfinalpreview.DataModel.GenStory
import com.example.androidappfinalpreview.R
import com.example.androidappfinalpreview.TTS.TTSProvider
import com.example.androidappfinalpreview.modelAI.GenerativeModelManager
import com.example.androidappfinalpreview.ui.theme.backgroundColor
import com.example.androidappfinalpreview.ui.theme.onBackgroundColor
import com.example.androidappfinalpreview.ui.theme.onPrimaryColor
import com.example.androidappfinalpreview.ui.theme.onSecondaryColor
import com.example.androidappfinalpreview.ui.theme.primaryColor
import com.example.androidappfinalpreview.ui.theme.secondaryColor
import kotlinx.coroutines.launch


@Composable
fun Screen1(navController: NavController) {

    // List of selectable options
    val options = listOf("Easy", "Medium", "Hard")
    var currentIndex by remember { mutableStateOf(0) }
    val currentOption = options[currentIndex]


    // Context for showing Toast messages
    val context = LocalContext.current
    val hapticFeedback = LocalHapticFeedback.current


    // Game menu UI
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top 75% section
            Box(
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Logo and game name
                    Image(
                        painter = painterResource(id = R.drawable.image_test), // Ensure this is a valid drawable resource
                        contentDescription = "Game Logo",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Comprehension",
                        fontSize = 24.sp,
                        color = onBackgroundColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                    // Difficulty options as buttons
                    Button(
                        onClick = {
//                            Toast.makeText(context, "Easy difficulty selected", Toast.LENGTH_SHORT).show()
                            // Add logic for selecting "Easy" difficulty
                            Difficulty.setDifficulty("Easy")
                                  navController.navigate("Screen2")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = onPrimaryColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = "Easy")
                    }


                    Button(
                        onClick = {
                            Difficulty.setDifficulty("Medium")
                            navController.navigate("Screen2")
//                            Toast.makeText(context, "Medium difficulty selected", Toast.LENGTH_SHORT).show()
                            // Add logic for selecting "Medium" difficulty
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = secondaryColor,
                            contentColor = onSecondaryColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = "Medium")
                    }


                    Button(
                        onClick = {
                            Difficulty.setDifficulty("Hard")
                            navController.navigate("Screen2")
//                            Toast.makeText(context, "Hard difficulty selected", Toast.LENGTH_SHORT).show()
                            // Add logic for selecting "Hard" difficulty
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = onPrimaryColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = "Hard")
                    }
                }
            }
            // Bottom 25% section for navigation accessibility
            var currentOption = options[0]
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset ->
                                val width = size.width
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                if (offset.x < width / 2) {
                                    // Left side tapped
                                    currentIndex = (currentIndex - 1 + options.size) % options.size
                                } else {
                                    // Right side tapped
                                    currentIndex = (currentIndex + 1) % options.size
                                }
                                currentOption = options[currentIndex]
                                // Show a toast message with the current option
//                                Toast
//                                    .makeText(
//                                        context,
//                                        "Current Option: $currentOption",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                    .show()
                                TTSProvider
                                    .get()
                                    ?.speak(currentOption)
                            },
                            onDoubleTap = {
                                // Show a toast message indicating the current option is selected
//                                Toast
//                                    .makeText(
//                                        context,
//                                        "Current Option Selected: $currentOption",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                    .show()
//                                if(currentOption == "Easy"){
//                                    TTSProvider.get()?.speak("$currentOption is selected")
//                                    navController.navigate("Screen2")
//                                }
//                                if(currentOption == "Medium"){
//                                    TTSProvider.get()?.speak("$currentOption is selected")
//                                }
//                                if(currentOption == "Hard"){
//                                    TTSProvider.get()?.speak("$currentOption is selected")
//                                }
                                when (currentOption) {
                                    "Easy" -> {
                                        TTSProvider
                                            .get()
                                            ?.speak("$currentOption is selected")
                                        Difficulty.setDifficulty("Easy")
                                        navController.navigate("Screen2")
                                    }

                                    "Medium" -> {
                                        TTSProvider
                                            .get()
                                            ?.speak("$currentOption is selected")
                                        Difficulty.setDifficulty("Medium")
                                        navController.navigate("Screen2")

                                    }

                                    "Hard" -> {
                                        TTSProvider
                                            .get()
                                            ?.speak("$currentOption is selected")
                                        Difficulty.setDifficulty("Hard")
                                        navController.navigate("Screen2")
                                    }
                                }
                            }
                        )
                    }
            )
        }
    }
}

//@Composable
//@Preview
//fun myPreview1(){
//    Screen1()
//}
