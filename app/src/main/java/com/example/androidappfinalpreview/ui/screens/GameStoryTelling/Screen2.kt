package com.example.androidappfinalpreview.ui.screens.GameStoryTelling

//import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidappfinalpreview.DataModel.Difficulty
import com.example.androidappfinalpreview.DataModel.GenStory
import com.example.androidappfinalpreview.TTS.TTSProvider
import com.example.androidappfinalpreview.modelAI.GenerativeModelManager
import com.example.androidappfinalpreview.ui.theme.*
import kotlinx.coroutines.launch


@Composable
fun Screen2(navController: NavController) {

    // Context for showing Toast messages
    val context = LocalContext.current
    val hapticFeedback = LocalHapticFeedback.current

    DisposableEffect(Unit){
        onDispose {
            TTSProvider.get()?.stopTTS()
        }
    }

    // List of selectable options
    val options = listOf("Read","Start Questions")
    var currentIndex by remember { mutableStateOf(0) }
    val currentOption = options[currentIndex]
    var storyText = ""
//  The Story Text
    if(Difficulty.getDifficulty() == "Easy") {
        storyText = GenStory.getEasyStory().trimIndent()
    }
    else if(Difficulty.getDifficulty() == "Medium") {
        storyText = GenStory.getMediumStory().trimIndent()
    }
    else{
        storyText = GenStory.getHardStory().trimIndent()
    }
//
//    val scope = rememberCoroutineScope()
//    var storyText = remember {
//        mutableStateOf("")
//    }
//    LaunchedEffect(Unit) {
//        scope.launch {
//            var response =
//                GenerativeModelManager.generateResponse("Generate a comprehension on our society of length 100 words")
//            if (response != null) {
//                storyText.value = response
//            }
//        }
//    }


    // Game screen UI
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top 75% section for story content and buttons
            Column(
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item {
                        Text(
                            text = storyText,
                            fontSize = 18.sp,
                            color = onBackgroundColor,
                            textAlign = TextAlign.Start
                        )
                    }
                }


                // Buttons for user actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Button(
                        onClick = {
                                  TTSProvider.get()?.speak(storyText)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = onPrimaryColor
                        )
                    ) {
                        Text(text = "Read")
                    }

//                    Button(
//                        onClick = {
//                            TTSProvider.get()?.speak(storyText)
//                        },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = primaryColor,
//                            contentColor = onPrimaryColor
//                        )
//                    ) {
//                        Text(text = "Replay")
//                    }

                    Button(
                        onClick = {
//                                  navController.navigate("Screen3")
                            if(GenStory.getEasyQuestions().size>0 && GenStory.getMediumQuestionSize()>0 && GenStory.getHardQuestionSize()>0) {
                                navController.navigate("Screen3")
                            }
                            else{
                                navController.navigate("LoadingScreen")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = secondaryColor,
                            contentColor = onSecondaryColor
                        )
                    ) {
                        Text(text = "Start Questions")
                    }
                }
            }

            var currentOption = options[0]
            // Bottom 25% section for navigation accessibility
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
                                when (currentOption) {
                                    "Read" -> {
                                        TTSProvider
                                            .get()
                                            ?.speak(storyText)
                                    }

                                    "Start Questions" -> {
                                        if (GenStory.getEasyQuestions().size > 0 && GenStory.getMediumQuestionSize() > 0 && GenStory.getHardQuestionSize() > 0) {
                                            navController.navigate("Screen3")
                                        } else {
                                            navController.navigate("LoadingScreen")
                                        }
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
//fun MyPreview(){
//    Screen2()
//}
