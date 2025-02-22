package com.example.androidappfinalpreview.DataModel

object Scores {

    private var scoreGame1 = 0
    private var scoreGame2 =0

    fun setScoresGame1(score:Int){
        scoreGame1=score
    }

    fun getScoresGame1():Int{
        return scoreGame1
    }

    fun setScoresGame2(score:Int){
        scoreGame2=score
    }

    fun getScoresGame2():Int{
        return scoreGame2
    }

}