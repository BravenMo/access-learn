package com.example.androidappfinalpreview.TTS

import android.content.Context

object TTSProvider {
    private var ttsManager: TTSManager? = null

    fun initialize(context: Context) {
        if (ttsManager == null) {
            ttsManager = TTSManager(context)
        }
    }

    fun get(): TTSManager? = ttsManager
}