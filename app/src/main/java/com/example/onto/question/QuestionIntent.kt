package com.example.onto.question

import com.example.onto.base.MviIntent

sealed class QuestionIntent : MviIntent {

    object InitialIntent : QuestionIntent()

    object GoBackIntent : QuestionIntent()

    data class SendQuestionIntent(
        val email: String,
        val question: String
    ) : QuestionIntent()

    data class SaveDraftIntent(
        val email: String,
        val question: String
    ) : QuestionIntent()

    object QuestionNothingIntent : QuestionIntent()

}