package com.example.androidappfinalpreview.DataModel

// Data class for Question
data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)