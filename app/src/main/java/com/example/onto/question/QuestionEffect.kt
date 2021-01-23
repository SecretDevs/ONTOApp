package com.example.onto.question

import com.example.onto.base.MviEffect

sealed class QuestionEffect : MviEffect {

    data class SavedValuesLoadedEffect(
        val email: String,
        val question: String
    ) : QuestionEffect()

    object PostingQuestionEffect : QuestionEffect()

    object QuestionPostedEffect : QuestionEffect()

    object QuestionPostingErrorEffect : QuestionEffect()

    object NoEffect : QuestionEffect()

}