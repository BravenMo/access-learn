package com.example.androidappfinalpreview.controller

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidappfinalpreview.ui.screens.GameStoryTelling.LoadingScreen
import com.example.androidappfinalpreview.ui.screens.GameStoryTelling.Screen1
import com.example.androidappfinalpreview.ui.screens.GameStoryTelling.Screen2
import com.example.androidappfinalpreview.ui.screens.GameStoryTelling.Screen3
import com.example.androidappfinalpreview.ui.screens.GameStoryTelling.Screen4
import com.example.androidappfinalpreview.ui.screens.GameWordAssociation.Game2Screen1
import com.example.androidappfinalpreview.ui.screens.GameWordAssociation.Game2Screen2
import com.example.androidappfinalpreview.ui.screens.GameWordAssociation.Game2Screen3
import com.example.androidappfinalpreview.ui.screens.HomeScreen.HomeScreen
import com.example.androidappfinalpreview.ui.screens.HomeScreen.SelectGame

@Composable
fun myNavHost(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "HomeScreen"){
        composable("HomeScreen"){
            HomeScreen(navController = navController)
        }
        composable("SelectGame"){
            SelectGame(navController = navController)
        }
        composable("Screen1"){
            Screen1(navController = navController)
        }
        composable("Screen2"){
            Screen2(navController = navController)
        }
        composable("Screen3"){
            Screen3(navController = navController)
        }
        composable("Screen4"){
            Screen4(navController = navController)
        }
        composable("Game2Screen1") {
            Game2Screen1(navController = navController)
        }
        composable("Game2Screen2"){
            Game2Screen2(navController = navController)
        }
        composable("Game2Screen3"){
            Game2Screen3(navController = navController)
        }
        composable("LoadingScreen"){
            LoadingScreen(navController = navController)
        }
    }
}