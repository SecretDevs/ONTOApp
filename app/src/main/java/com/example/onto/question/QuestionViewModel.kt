package com.example.onto.question

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.ProfilePrefsUseCase
import com.example.onto.data.usecase.QuestionUseCase
import com.example.onto.navigation.Coordinator

class QuestionViewModel @ViewModelInject constructor(
    private val coordinator: Coordinator,
    private val profilePrefsUseCase: ProfilePrefsUseCase,
    private val questionUseCase: QuestionUseCase
) : BaseViewModel<QuestionViewState, QuestionEffect, QuestionIntent, QuestionAction>() {
    override fun initialState(): QuestionViewState = QuestionViewState()

    override fun intentInterpreter(intent: QuestionIntent): QuestionAction =
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
        }

    override suspend fun performAction(action: QuestionAction): QuestionEffect =
        when (action) {
            QuestionAction.LoadSavedAction -> {
                QuestionEffect.SavedValuesLoadedEffect(
                    email = if (questionUseCase.getEmail()
                            .isNotEmpty()
                    ) questionUseCase.getEmail() else profilePrefsUseCase.getEmail(),
                    question = questionUseCase.getMessage()
                )
            }
            QuestionAction.GoBackAction -> {
                coordinator.pop()
                QuestionEffect.NoEffect
            }
            is QuestionAction.PostQuestionAction -> {
                //TODO: posting
                questionUseCase.apply {
                    setEmail("")
                    setMessage("")
                }
                QuestionEffect.PostingQuestionEffect
            }
            is QuestionAction.SaveDraftAction -> {
                questionUseCase.apply {
                    setEmail(action.email)
                    setMessage(action.question)
                }
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