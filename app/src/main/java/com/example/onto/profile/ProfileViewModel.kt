package com.example.onto.profile

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.utils.Result

class ProfileViewModel @ViewModelInject constructor(
    private val profileUseCase: ProfileUseCase
) : BaseViewModel<ProfileViewState, ProfileEffect, ProfileIntent, ProfileAction>() {
    override fun initialState(): ProfileViewState = ProfileViewState()

    override fun intentInterpreter(intent: ProfileIntent): ProfileAction =
        when (intent) {
            ProfileIntent.InitialIntent -> ProfileAction.LoadProfileAction
            ProfileIntent.ReloadIntent -> ProfileAction.LoadProfileAction
        }

    override suspend fun performAction(action: ProfileAction): ProfileEffect =
        when (action) {
            ProfileAction.LoadProfileAction -> {
                addIntermediateEffect(ProfileEffect.InitialLoadingEffect)
                when (val result = profileUseCase.getUserProfile(0L)) {
                    is Result.Success -> ProfileEffect.UserLoadedEffect(result.data)
                    is Result.Error -> ProfileEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
        }

    override fun stateReducer(oldState: ProfileViewState, effect: ProfileEffect): ProfileViewState =
        when (effect) {
            ProfileEffect.InitialLoadingEffect -> ProfileViewState(
                isInitialLoading = true,
                initialLoadingError = null,
                user = null
            )
            is ProfileEffect.InitialLoadingErrorEffect -> ProfileViewState(
                isInitialLoading = false,
                initialLoadingError = effect.throwable,
                user = null
            )
            is ProfileEffect.UserLoadedEffect -> ProfileViewState(
                isInitialLoading = false,
                initialLoadingError = null,
                user = effect.user
            )
        }

}