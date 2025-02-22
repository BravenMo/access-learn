package com.example.androidappfinalpreview.TTS

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TTSManager(private val context: Context) {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("en", "IN") // Setting Indian English locale
                isInitialized = true
            }
        }
    }

    fun speak(text: String) {
        if (isInitialized) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun shutDown() {
        tts?.shutdown()
    }

    fun stopTTS(){
        tts?.stop()
    }
}
