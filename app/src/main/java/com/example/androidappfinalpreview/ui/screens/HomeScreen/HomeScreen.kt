package com.example.androidappfinalpreview.ui.screens.HomeScreen

//import android.widget.Toast
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.androidappfinalpreview.R
import com.example.androidappfinalpreview.TTS.TTSProvider
import com.example.androidappfinalpreview.ui.theme.backgroundColor
import com.example.androidappfinalpreview.ui.theme.onBackgroundColor
import com.example.androidappfinalpreview.ui.theme.onPrimaryColor
import com.example.androidappfinalpreview.ui.theme.onSecondaryColor
import com.example.androidappfinalpreview.ui.theme.primaryColor
import com.example.androidappfinalpreview.ui.theme.secondaryColor

@Composable
fun HomeScreen(navController: NavController) {
//    val options = listOf("PLAY")
//    var currentIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current

    // For haptic feedback
    val hapticFeedback = LocalHapticFeedback.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor) // Background color of the screen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp)) // Optional: Add top spacing

            // Middle content: App name, subtitle, image, and buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Occupy available vertical space
                    .padding(horizontal = 24.dp), // Side margins
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image (replace with your own image resource or a placeholder)
                Image(
                    painter = painterResource(id = R.drawable.palette_swatch), // Replace with your image resource
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp)
                )

                // App name and subtitle
                Text(
                    text = "LEARN IT",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White // Text color white
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "A Learning Application",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White // Text color white
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp)) // Spacing between text and buttons

                // Manually setting button width based on expected size
                val buttonWidth = 150.dp // You can adjust this width as needed

                // Buttons with consistent width
                Button(
                    onClick = { navController.navigate("SelectGame") },
                    modifier = Modifier
                        .width(buttonWidth)
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, // Button background color white
                        contentColor = primaryColor // Text color purple (same as primary)
                    ),
                    shape = RoundedCornerShape(30) // Rounded button shape
                ) {
                    Text(
                        "PLAY",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = primaryColor, // Text color purple (primary color)
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
//                Button(
//                    onClick = { /* TODO: Options action */ },
//                    modifier = Modifier
//                        .width(buttonWidth)
//                        .padding(vertical = 8.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.White, // Button background color white
//                        contentColor = primaryColor // Text color purple (same as primary)
//                    ),
//                    shape = RoundedCornerShape(30) // Rounded button shape
//                ) {
//                    Text(
//                        "Options",
//                        style = MaterialTheme.typography.bodyLarge.copy(
//                            color = primaryColor, // Text color purple (primary color)
//                            fontWeight = FontWeight.SemiBold
//                        )
//                    )
//                }
            }

            // Bottom touch area (no changes unless you need additional modifications)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset ->
                                val width = size.width
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
//                                if (offset.x < width / 2) {
//                                    // Left side tapped
//                                    currentIndex = (currentIndex - 1 + options.size) % options.size
//                                } else {
//                                    // Right side tapped
//                                    currentIndex = (currentIndex + 1) % options.size
//                                }
//                                val currentOption = options[currentIndex]
                                // Read out the current option
//                                Toast
//                                    .makeText(
//                                        context,
//                                        "Current Option: $currentOption",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                    .show()
                                TTSProvider
                                    .get()
                                    ?.speak("Play")
                            },
                            onDoubleTap = {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
//                                val currentOption = options[currentIndex]
//                                Toast
//                                    .makeText(
//                                        context,
//                                        "Current Option Selected: $currentOption",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                    .show()
                                TTSProvider.get()?.speak("Play is selected")
//                                when (currentOption) {
//                                    "Play" -> {
                                        navController.navigate("SelectGame")
//                                    }
//                                    "Options" -> {
//                                        // TODO: Navigate to Options screen
//                                    }
//                                }
                            }
                        )
                    }
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewMainScreen() {
//        HomeScreen()
//}