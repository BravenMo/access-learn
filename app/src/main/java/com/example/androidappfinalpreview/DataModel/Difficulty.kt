package com.example.androidappfinalpreview.DataModel

object Difficulty {

    private var difficulty:String = ""

    fun setDifficulty(x:String){
        difficulty = x
    }

    fun getDifficulty():String{
        return difficulty
    }
}