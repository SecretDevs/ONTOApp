package com.example.onto.question

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.navigation.Coordinator

class QuestionViewModel @ViewModelInject constructor(
    private val coordinator: Coordinator
) : BaseViewModel<QuestionViewState, QuestionEffect, QuestionIntent, QuestionAction>() {
    override fun initialState(): QuestionViewState = QuestionViewState()

    override fun intentInterpreter(intent: QuestionIntent): QuestionAction  =
        when (intent) {
            QuestionIntent.InitialIntent -> QuestionAction.LoadSavedAction
            QuestionIntent.GoBackIntent -> QuestionAction.GoBackAction
            is QuestionIntent.SendQuestionIntent -> QuestionAction.PostQuestionAction(
                intent.email,
                intent.question
            )
            is QuestionIntent.SaveDraftIntent -> QuestionAction.SaveDraftAction(
                intent.email,
                intent.question
            )
            QuestionIntent.QuestionNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
        }

    override suspend fun performAction(action: QuestionAction): QuestionEffect =
        when (action) {
            QuestionAction.LoadSavedAction -> {
                //TODO: saving and storing
                QuestionEffect.SavedValuesLoadedEffect(null, null)
            }
            QuestionAction.GoBackAction -> {
                coordinator.pop()
                QuestionEffect.NoEffect
            }
            is QuestionAction.PostQuestionAction -> {
                //TODO: posting
                QuestionEffect.PostingQuestionEffect
            }
            is QuestionAction.SaveDraftAction -> {
                //TODO: saving and storing
                QuestionEffect.NoEffect
            }
        }

    override fun stateReducer(
        oldState: QuestionViewState,
        effect: QuestionEffect
    ): QuestionViewState =
        when (effect) {
            is QuestionEffect.SavedValuesLoadedEffect -> QuestionViewState(
                savedEmail = effect.email,
                savedMessage = effect.question,
                isLoading = false,
                isLoaded = null
            )
            QuestionEffect.PostingQuestionEffect -> QuestionViewState(
                savedEmail = oldState.savedEmail,
                savedMessage = oldState.savedMessage,
                isLoading = true,
                isLoaded = null
            )
            is QuestionEffect.QuestionPostedEffect -> QuestionViewState(
                savedEmail = null,
                savedMessage = null,
                isLoading = false,
                isLoaded = true
            )
            QuestionEffect.NoEffect -> oldState
            QuestionEffect.QuestionPostingErrorEffect -> QuestionViewState(
                savedEmail = oldState.savedEmail,
                savedMessage = oldState.savedMessage,
                isLoading = false,
                isLoaded = false
            )
        }
}