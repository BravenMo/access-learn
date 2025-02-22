package com.example.androidappfinalpreview.modelAI

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TestModel(){
    var textToDisplay = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    Column {
        Button(onClick = {
            scope.launch {
                val result = GenerativeModelManager.generateResponse("Generate a story on India")
                if (result != null) {
                    textToDisplay.value=result
                }
            }
        }) {
            Text("Click Me")
        }
        Text(text = textToDisplay.value)
    }
}