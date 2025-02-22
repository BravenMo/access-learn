//package com.example.androidappfinalpreview.modelAI
//
//import com.google.ai.client.generativeai.GenerativeModel
//
//object GenerativeModelManager {
//    private val generativeModel: GenerativeModel by lazy {
//        loadModel()
//    }
//
//    private fun loadModel(): GenerativeModel {
//        return GenerativeModel(
//            modelName = "gemini-1.5-flash",
//            apiKey = <<your-key>>
//        )
//    }
//
//    suspend fun generateResponse(prompt: String): String? {
//        return try {
//            val response = generativeModel.generateContent(prompt)
//            response.text
//        } catch (e: Exception) {
//            // Handle or log exceptions
//            null
//        }
//    }
//}

package com.example.androidappfinalpreview.modelAI

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

object GenerativeModelManager {
    private const val OPENAI_API_URL = "https://api.openai.com/v1/chat/completions"
    private const val API_KEY = <<your-key>>

    // Configure OkHttpClient with custom timeouts
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .cache(null)
        .build()

    suspend fun generateResponse(prompt: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val messages = JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", prompt)
                    })
                }

                val json = JSONObject().apply {
                    put("model", "gpt-3.5-turbo") // Change if you don't have access to gpt-4
                    put("messages", messages)
                }

                val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json.toString())
                val request = Request.Builder()
                    .url(OPENAI_API_URL)
                    .header("Authorization", "Bearer $API_KEY")
                    .header("Cache-Control", "no-cache")
                    .header("Pragma", "no-cache")
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()

                // Logging response details for debugging
                if (!response.isSuccessful) {
                    Log.e("GenerativeModelManager", "Request failed with status code: ${response.code}")
                    Log.e("GenerativeModelManager", "Response message: ${response.message}")
                    Log.e("GenerativeModelManager", "Response body: ${response.body?.string()}")
                    return@withContext null
                }

                val responseBody = response.body?.string()
                val jsonResponse = JSONObject(responseBody ?: "")
                val choices = jsonResponse.optJSONArray("choices")
                if (choices != null && choices.length() > 0) {
                    choices.getJSONObject(0).getJSONObject("message").getString("content")
                } else {
                    Log.e("GenerativeModelManager", "No choices found in response")
                    null
                }
            } catch (e: IOException) {
                Log.e("GenerativeModelManager", "Network request failed", e)
                null
            } catch (e: Exception) {
                Log.e("GenerativeModelManager", "An error occurred", e)
                null
            }
        }
    }
}

