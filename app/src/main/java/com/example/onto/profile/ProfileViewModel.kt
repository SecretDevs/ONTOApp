package com.example.onto.profile

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.CartUseCase
import com.example.onto.data.usecase.ProfileUseCase
import com.example.onto.navigation.Coordinator
import com.example.onto.utils.Result

class ProfileViewModel @ViewModelInject constructor(
    private val profileUseCase: ProfileUseCase,
    private val cartUseCase: CartUseCase,
    private val coordinator: Coordinator
) : BaseViewModel<ProfileViewState, ProfileEffect, ProfileIntent, ProfileAction>() {
    override fun initialState(): ProfileViewState = ProfileViewState()

    override fun intentInterpreter(intent: ProfileIntent): ProfileAction =
        when (intent) {
            ProfileIntent.InitialIntent -> ProfileAction.LoadProfileAction
            ProfileIntent.ReloadIntent -> ProfileAction.LoadProfileAction
            ProfileIntent.GoToQuestionIntent -> ProfileAction.NavigateToQuestionAction
            ProfileIntent.GoToCartIntent -> ProfileAction.NavigateToCartAction
            ProfileIntent.ProfileNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
        }

    override suspend fun performAction(action: ProfileAction): ProfileEffect =
        when (action) {
            ProfileAction.LoadProfileAction -> {
                addIntermediateEffect(ProfileEffect.InitialLoadingEffect)
                addIntermediateEffect(
                    ProfileEffect.CartInformationLoaded(
                        when (val result = cartUseCase.getCartInformation()) {
                            is Result.Success -> result.data
                            is Result.Error -> null
                        }
                    )
                )
                when (val result = profileUseCase.getUserProfile(0L)) {
                    is Result.Success -> ProfileEffect.UserLoadedEffect(result.data)
                    is Result.Error -> ProfileEffect.InitialLoadingErrorEffect(result.throwable)
                }
            }
            ProfileAction.NavigateToQuestionAction -> {
                coordinator.navigateToQuestion()
                ProfileEffect.NoEffect
            }
            ProfileAction.NavigateToCartAction -> {
                coordinator.navigateToCart()
                ProfileEffect.NoEffect
            }
        }

    override fun stateReducer(oldState: ProfileViewState, effect: ProfileEffect): ProfileViewState =
        when (effect) {
            ProfileEffect.InitialLoadingEffect -> ProfileViewState(
                isInitialLoading = true,
                initialLoadingError = null,
                user = null,
                cartInformation = null
            )
            is ProfileEffect.InitialLoadingErrorEffect -> ProfileViewState(
                isInitialLoading = false,
                initialLoadingError = effect.throwable,
                user = null,
                cartInformation = oldState.cartInformation
            )
            is ProfileEffect.UserLoadedEffect -> ProfileViewState(
                isInitialLoading = false,
                initialLoadingError = null,
                user = effect.user,
                cartInformation = oldState.cartInformation
            )
            is ProfileEffect.CartInformationLoaded -> ProfileViewState(
                isInitialLoading = oldState.isInitialLoading,
                initialLoadingError = oldState.initialLoadingError,
                user = oldState.user,
                cartInformation = effect.cartInformation
            )
            ProfileEffect.NoEffect -> oldState
        }

}