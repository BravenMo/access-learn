package com.example.androidappfinalpreview

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.androidappfinalpreview.DataModel.GenStory
import com.example.androidappfinalpreview.TTS.TTSProvider
import com.example.androidappfinalpreview.controller.myNavHost
import com.example.androidappfinalpreview.modelAI.GenerativeModelManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.json.JSONArray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TTSProvider.initialize(this)
        enableEdgeToEdge()
        setContent {
            myNavHost()
        }

        lifecycleScope.launch {
            val text1 = GenStory.getEasyStory()
            var response1 = GenerativeModelManager.generateResponse(
//                "Generate 10 questions on the story: $text. Give it with options and the index of the right answer starting at 0, with a difficulty level = $diff which ranges from 0 to 1. Only give a JSON format of the questions which should be in the format question, options and answer. Do not add any other information. No need to mention the difficulty level"
                "Generate 10 difficult questions on the story: $text1. Give it with options and the index of the right answer starting at 0. The output should only be a JSON of the questions which should be in the format question, options and answer starting with [. Do not add any other information."
            )
            if(response1!=null) {
                Log.i("MainActivityMohit", response1)
                // Convert the input JSON string into a JSONArray
                val jsonArray = JSONArray(response1)
                // Iterate over the array and extract the data
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val questionText = jsonObject.getString("question")
                    val optionsArray = jsonObject.getJSONArray("options")
                    val correctIndex = jsonObject.getInt("answer")
                    // Convert options JSONArray to a List<String>
                    val optionsList = mutableListOf<String>()
                    for (j in 0 until optionsArray.length()) {
                        optionsList.add(optionsArray.getString(j))
                    }
                    // Create a Question object and add it to the list
                    GenStory.setEasyQuestions(questionText, optionsList, correctIndex)
                }
            }

            val text2 = GenStory.getMediumStory()
            var response2 = GenerativeModelManager.generateResponse(
//                "Generate 10 questions on the story: $text. Give it with options and the index of the right answer starting at 0, with a difficulty level = $diff which ranges from 0 to 1. Only give a JSON format of the questions which should be in the format question, options and answer. Do not add any other information. No need to mention the difficulty level"
                "Generate 10 difficult questions on the story: $text2. Give it with options and the index of the right answer starting at 0. The output should only be a JSON of the questions which should be in the format question, options and answer starting with [. Do not add any other information."
            )
            if(response2!=null) {
                Log.i("MainActivityMohit", response2)
                // Convert the input JSON string into a JSONArray
                val jsonArray = JSONArray(response2)
                // Iterate over the array and extract the data
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val questionText = jsonObject.getString("question")
                    val optionsArray = jsonObject.getJSONArray("options")
                    val correctIndex = jsonObject.getInt("answer")
                    // Convert options JSONArray to a List<String>
                    val optionsList = mutableListOf<String>()
                    for (j in 0 until optionsArray.length()) {
                        optionsList.add(optionsArray.getString(j))
                    }
                    // Create a Question object and add it to the list
                    GenStory.setMediumQuestions(questionText, optionsList, correctIndex)
                }
            }

            val text3 = GenStory.getHardStory()
            var response3 = GenerativeModelManager.generateResponse(
//                "Generate 10 questions on the story: $text. Give it with options and the index of the right answer starting at 0, with a difficulty level = $diff which ranges from 0 to 1. Only give a JSON format of the questions which should be in the format question, options and answer. Do not add any other information. No need to mention the difficulty level"
                "Generate 10 difficult questions on the story: $text3. Give it with options and the index of the right answer starting at 0. The output should only be a JSON of the questions which should be in the format question, options and answer starting with [. Do not add any other information."
            )
            if(response3!=null) {
                Log.i("MainActivityMohit", response3)
                // Convert the input JSON string into a JSONArray
                val jsonArray = JSONArray(response3)
                // Iterate over the array and extract the data
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val questionText = jsonObject.getString("question")
                    val optionsArray = jsonObject.getJSONArray("options")
                    val correctIndex = jsonObject.getInt("answer")
                    // Convert options JSONArray to a List<String>
                    val optionsList = mutableListOf<String>()
                    for (j in 0 until optionsArray.length()) {
                        optionsList.add(optionsArray.getString(j))
                    }
                    // Create a Question object and add it to the list
                    GenStory.setHardQuestions(questionText, optionsList, correctIndex)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
