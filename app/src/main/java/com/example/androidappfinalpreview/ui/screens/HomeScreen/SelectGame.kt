package com.example.androidappfinalpreview.ui.screens.HomeScreen

import android.os.Bundle
//import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.androidappfinalpreview.R
import com.example.androidappfinalpreview.TTS.TTSProvider
import com.example.androidappfinalpreview.ui.theme.primaryColor
import com.example.androidappfinalpreview.ui.theme.secondaryColor

@Composable
fun SelectGame(navController:NavController) {
    val gameImages = listOf(
        R.drawable.image_test,
        R.drawable.words
    )
    val imageNames = listOf("Comprehension", "Word Association")
    var currentIndex by remember { mutableStateOf(0) }
    var destinations = listOf("Screen1","Game2Screen1")
    val hapticFeedback = LocalHapticFeedback.current
    val context = LocalContext.current


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Carousel Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Display images with a layered effect
            gameImages.forEachIndexed { index, imageRes ->
                val isCurrent = index == currentIndex
                val scale by animateFloatAsState(
                    targetValue = if (isCurrent) 1f else 0.8f,
                    animationSpec = tween(300)
                )
                val alpha by animateFloatAsState(
                    targetValue = if (isCurrent) 1f else 0.5f,
                    animationSpec = tween(300)
                )
                val translationX by animateFloatAsState(
                    targetValue = when {
                        index < currentIndex -> -150f
                        index > currentIndex -> 150f
                        else -> 0f
                    }, animationSpec = tween(300)
                )


                Box(
                    modifier = Modifier
                        .offset(x = translationX.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                        .zIndex(if (isCurrent) 1f else 0f)
                        .clickable {
                            navController.navigate(destinations[currentIndex])
                        }
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Game ${index + 1}",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(
                                if (isCurrent) 350.dp else 300.dp,
                                if (isCurrent) 250.dp else 200.dp
                            )
                            .clip(RoundedCornerShape(16.dp))
                            .shadow(if (isCurrent) 8.dp else 4.dp, RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }


            // Left Navigation Button (Circular)
            FloatingActionButton(
                onClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    currentIndex = (currentIndex - 1 + gameImages.size) % gameImages.size
                },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(8.dp)
                    .size(40.dp) // Smaller size for the circular button
                    .zIndex(2f), // Ensures the button is above all other elements
                containerColor = primaryColor
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous",
                    tint = Color.White // Adjusts the icon color for contrast
                )
            }


            // Right Navigation Button (Circular)
            FloatingActionButton(
                onClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    currentIndex = (currentIndex + 1) % gameImages.size
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(8.dp)
                    .size(40.dp) // Smaller size for the circular button
                    .zIndex(2f), // Ensures the button is above all other elements
                containerColor = primaryColor
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next",
                    tint = Color.White // Adjusts the icon color for contrast
                )
            }
        }


        // Display the name of the current image, moved below the carousel
        Text(
            text = imageNames[currentIndex],
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )


        // Bottom 25% Touch Area for Interaction
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            val width = size.width
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (it.x < width / 2) {
                                // Navigate to the previous image
                                currentIndex =
                                    (currentIndex - 1 + gameImages.size) % gameImages.size
                            } else {
                                // Navigate to the next image
                                currentIndex = (currentIndex + 1) % gameImages.size
                            }
                            // Show a toast with the current image name
//                            Toast
//                                .makeText(
//                                    context,
//                                    "Current: ${imageNames[currentIndex]}",
//                                    Toast.LENGTH_SHORT
//                                )
//                                .show()
                            val name=imageNames[currentIndex]
                            TTSProvider.get()?.speak(name)
                        },
                        onDoubleTap = {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            // Show a toast indicating the selection of the current image
//                            Toast
//                                .makeText(
//                                    context,
//                                    "Selected: ${imageNames[currentIndex]}",
//                                    Toast.LENGTH_SHORT
//                                )
//                                .show()
                            val name=imageNames[currentIndex]
                            TTSProvider.get()?.speak("$name is selected")
                            navController.navigate(destinations[currentIndex])
                        }
                    )
                }
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewPlayPage() {
//    SelectGame()
//}
