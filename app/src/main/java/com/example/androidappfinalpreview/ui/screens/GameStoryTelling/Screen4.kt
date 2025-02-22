package com.example.androidappfinalpreview.ui.screens.GameStoryTelling

import android.content.Intent
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidappfinalpreview.DataModel.GenStory
import com.example.androidappfinalpreview.DataModel.Scores
import com.example.androidappfinalpreview.ui.theme.primaryColor
import com.example.androidappfinalpreview.ui.theme.onBackgroundColor
import com.example.androidappfinalpreview.ui.theme.secondaryColor
import com.example.androidappfinalpreview.ui.theme.onPrimaryColor
import com.example.androidappfinalpreview.ui.theme.onSecondaryColor
import com.example.androidappfinalpreview.R // Replace with your actual resource path for the sound file
import com.example.androidappfinalpreview.TTS.TTSProvider


@Composable
fun Screen4(navController: NavController) {

    val context = LocalContext.current
    val scores=Scores.getScoresGame1()

    //Play victory sound when this composable is displayed
    LaunchedEffect(Unit) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.success_fanfare) // Ensure this is your actual sound resource
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release() // Release resources once done
        }
    }


    // Hardcoded score percentage
    val scoreP =((scores/10.0)*100.0)
    val scorePercentage = scoreP.toInt()
    val hapticFeedback = LocalHapticFeedback.current


    // List of selectable options
    val options = listOf("Score", "Retry", "Other games", "Share")
    var currentIndex by remember { mutableStateOf(0) }
    val currentOption = options[currentIndex]


    // Game score screen UI
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = secondaryColor // Background color set to secondaryColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp), // Increased top padding to move the content lower
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Outer Box wrapping all content with increased width
            Box(
                modifier = Modifier
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth(0.85f) // Increased width for the outer box
                    .padding(horizontal = 12.dp, vertical = 16.dp), // Slightly increased padding
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Score Display without a Box
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Score",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = onBackgroundColor,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "$scorePercentage%",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    }


                    // Styled Retry Button with elongated width
                    Button(
                        onClick = {
                                  navController.navigate("Screen1")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = onPrimaryColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.8f) // Increased width
                            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
                    ) {
                        Text(
                            text = "Retry",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }


                    // Styled Other Games Button with elongated width
                    Button(
                        onClick = {
                                  navController.navigate("SelectGame")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = secondaryColor,
                            contentColor = onSecondaryColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.8f) // Increased width
                            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
                    ) {
                        Text(
                            text = "Other games",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    // Share Icon/Button
                    IconButton(
                        onClick = {
                            // Intent to share score
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "I scored $scorePercentage% on the game!")
                                type = "text/plain"
                            }
                            context.startActivity(
                                Intent.createChooser(shareIntent, "Share your score")
                            )
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.share_variant), // Replace with your share icon resource
                            contentDescription = "Share Score",
                            tint = onBackgroundColor
                        )
                    }
                }
            }

            // Bottom 25% for navigation accessibility
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
//                                Toast.makeText(context, "Current Option: $currentOption", Toast.LENGTH_SHORT).show()
                                TTSProvider.get()?.speak(currentOption)
                            },
                            onDoubleTap = {
                                // Show a toast message indicating the current option is selected
//                                Toast.makeText(context, "Current Option Selected: $currentOption", Toast.LENGTH_SHORT).show()
                                when(currentOption) {
                                    "Score"->{
                                        TTSProvider.get()?.speak("Your score is $scorePercentage percent")
                                    }
                                    "Retry"->{
                                        TTSProvider.get()?.speak("$currentOption is selected")
                                        navController.navigate("Screen1")
                                    }
                                    "Other games"->{
                                        TTSProvider.get()?.speak("$currentOption is selected")
                                        navController.navigate("SelectGame")
                                    }
                                    "Share"->{
                                        TTSProvider.get()?.speak("$currentOption is selected")
                                        val shareIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_TEXT, "I scored $scorePercentage% on the game!")
                                            type = "text/plain"
                                        }
                                        context.startActivity(
                                            Intent.createChooser(shareIntent, "Share your score")
                                        )
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
//fun ScoreScreenPreview() {
//    ScoreScreen()
//}
