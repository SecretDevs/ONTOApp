package com.example.onto.question

import com.example.onto.base.MviAction

sealed class QuestionAction : MviAction {

    object LoadSavedAction : QuestionAction()

    object GoBackAction : QuestionAction()

    data class PostQuestionAction(
        val email: String,
        val question: String
    ) : QuestionAction()

    data class SaveDraftAction(
        val email: String,
        val question: String
    ) : QuestionAction()

}