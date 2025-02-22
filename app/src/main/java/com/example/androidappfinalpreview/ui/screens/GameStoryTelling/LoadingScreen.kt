package com.example.androidappfinalpreview.ui.screens.GameStoryTelling

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.androidappfinalpreview.DataModel.GenStory
import com.example.androidappfinalpreview.ui.theme.*
import kotlinx.coroutines.delay


//@Composable
//fun LoadingScreen(navController: NavController) {
//
//    var curr = remember {
//        mutableStateOf(GenStory.getQuestions().size)
//    }
//
//    if(curr.value>0){
//        navController.navigate("Screen3")
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(primaryColor), // Setting the background color
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Loading...",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                color = onPrimaryColor // Using onPrimaryColor for contrast with background
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            CircularProgressIndicator(
//                color = secondaryColor // Setting the progress indicator color
//            )
//        }
//    }
//}

@Composable
fun LoadingScreen(navController: NavController) {
        // UI to display the loading screen

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000L) // 10-second delay
            // Check if questions list is updated
            if (GenStory.getEasyQuestionSize()>0 && GenStory.getMediumQuestionSize()>0 && GenStory.getHardQuestionSize()>0) {
                navController.navigate("Screen3")
                break // Exit the loop once navigation is triggered
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor), // Setting the background color
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Loading...",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = onPrimaryColor // Using onPrimaryColor for contrast with background
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(
                color = secondaryColor // Setting the progress indicator color
            )
        }
    }
//    while(true){
//        val questionSize = GenStory.getQuestionSize()
//        if(questionSize>0){
//            navController.navigate("Screen3")
//        }
//    }
}

//@Preview
//@Composable
//fun Preview(){
//    LoadingScreen()
//}
