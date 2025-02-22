package com.example.androidappfinalpreview.DataModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object GenStory{


    private var easyStoryText="Ella inherited a strange clock from her grandmother. It chimed at odd hours, even when unplugged. One night, she noticed the hands moving backward. Each time it struck midnight, a faint voice whispered, \"Remember.\" Ella discovered old journals hidden in her grandmother’s closet. The entries told stories of bravery and difficult choices during a family feud. Ella realized the clock was a reminder of forgiveness. She decided to reconnect with estranged relatives, bringing her family closer together."
    private var mediumStoryText="Every morning, Ethan crossed an old stone bridge on his way to school. The bridge, covered in ivy and moss, was said to be cursed: if you spoke while crossing, you’d lose something precious. Ethan didn’t believe in curses, but he loved the stillness of the bridge.\n" +
            "\n" +
            "One day, he saw a boy struggling to carry books across. Forgetting the rule, Ethan said, \"Need help?\" The boy smiled but vanished into thin air. The next day, Ethan’s voice was gone. He panicked but soon discovered he could communicate with gestures. Over time, he realized his silence made him a better listener. As weeks passed, his friendships deepened, and he became known for his quiet wisdom. One morning, the boy reappeared on the bridge and returned Ethan’s voice, whispering, \"Now you understand the power of silence.\" Ethan crossed the bridge silently from then on, valuing its lesson."
    private var hardStoryText="In a bustling city, Ava inherited an old house from her late aunt. Most rooms were ordinary, but one door was painted crimson and locked tightly. Her aunt had warned her never to open it, saying, \"It holds what you fear most.\" Ava dismissed it as a quirky superstition.\n" +
            "\n" +
            "One stormy night, curiosity got the better of her. She pried the door open and found herself standing in a room identical to her childhood bedroom. On the bed sat a younger version of herself, sobbing. The child whispered, \"You left me.\" Memories Ava had buried flooded back: broken promises, failures, and regrets. She fled, slamming the door behind her.\n" +
            "\n" +
            "Over the next days, Ava couldn’t stop thinking about the room. She realized that facing her fears wasn’t about running away but confronting them. Returning to the door, she stepped inside and sat beside the child, whispering, \"I’m here now.\" The child smiled, and the room dissolved into light. The door was now plain wood.\n" +
            "\n" +
            "Ava kept the door unlocked, a reminder that fears lose power when faced. She began rebuilding her life, understanding that the crimson door was never about fear itself—it was about learning to embrace her past."

    fun getEasyStory():String{
        return easyStoryText
    }

    fun getMediumStory():String{
        return mediumStoryText
    }

    fun getHardStory():String{
        return hardStoryText
    }

    private var easyQuestions = mutableListOf<Question>()
    private var mediumQuestions = mutableListOf<Question>()
    private var hardQuestions = mutableListOf<Question>()

    fun setEasyQuestions(question:String,options:List<String>,correctAnswer:Int){
        easyQuestions.add(
            Question(
                question,
                options,
                correctAnswer
            )
        )
    }

    fun setMediumQuestions(question:String,options:List<String>,correctAnswer:Int){
        mediumQuestions.add(
            Question(
                question,
                options,
                correctAnswer
            )
        )
    }

    fun setHardQuestions(question:String,options:List<String>,correctAnswer:Int){
        hardQuestions.add(
            Question(
                question,
                options,
                correctAnswer
            )
        )
    }

    fun getEasyQuestions():MutableList<Question>{
        return easyQuestions
    }

    fun getMediumQuestions():MutableList<Question>{
        return mediumQuestions
    }

    fun getHardQuestions():MutableList<Question>{
        return hardQuestions
    }

    fun getEasyQuestionSize():Int{
        return easyQuestions.size
    }

    fun getMediumQuestionSize():Int{
        return mediumQuestions.size
    }

    fun getHardQuestionSize():Int{
        return hardQuestions.size
    }

}