package com.example.onto.profile

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.CartUseCase
import com.example.onto.data.usecase.ProfilePrefsUseCase
import com.example.onto.data.usecase.ProfileUseCase
import com.example.onto.navigation.Coordinator
import com.example.onto.utils.Result

class ProfileViewModel @ViewModelInject constructor(
    private val profileUseCase: ProfileUseCase,
    private val profilePrefsUseCase: ProfilePrefsUseCase,
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
            is ProfileIntent.SaveProfileIntent -> ProfileAction.SaveProfileAction(
                firstName = intent.firstName,
                lastName = intent.lastName,
                email = intent.email,
                phone = intent.phone,
                useForDelivery = intent.useForDelivery,
                city = intent.city,
                street = intent.street,
                house = intent.house,
                building = intent.building,
                apartment = intent.apartment
            )
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
                    is Result.Success -> {
                        profilePrefsUseCase.apply {
                            setFirstName(result.data.firstName)
                            setLastName(result.data.lastName)
                            setEmail(result.data.email)
                            setPhone(result.data.phone)
                            setUserCity(result.data.address.city)
                            setUserStreet(result.data.address.street)
                            setUserHouse(result.data.address.house)
                            setUserApartment(result.data.address.apartment)
                        }
                        ProfileEffect.UserLoadedEffect(
                            result.data,
                            profilePrefsUseCase.isUsingForDelivery()
                        )
                    }
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
            is ProfileAction.SaveProfileAction -> {
                profilePrefsUseCase.apply {
                    setFirstName(action.firstName)
                    setLastName(action.lastName)
                    setEmail(action.email)
                    setPhone(action.phone)
                    setUserCity(action.city)
                    setUserStreet(action.street)
                    setUserHouse(action.house)
                    setUserApartment(action.apartment)
                    setUsageForDelivery(action.useForDelivery)
                }
                ProfileEffect.CartInformationLoaded(
                    when (val result = cartUseCase.getCartInformation()) {
                        is Result.Success -> result.data
                        is Result.Error -> null
                    }
                )
            }
        }

    override fun stateReducer(oldState: ProfileViewState, effect: ProfileEffect): ProfileViewState =
        when (effect) {
            ProfileEffect.InitialLoadingEffect -> ProfileViewState(
                isInitialLoading = true,
                initialLoadingError = null,
                user = null,
                cartInformation = null,
                forDelivery = oldState.forDelivery
            )
            is ProfileEffect.InitialLoadingErrorEffect -> ProfileViewState(
                isInitialLoading = false,
                initialLoadingError = effect.throwable,
                user = null,
                cartInformation = oldState.cartInformation,
                forDelivery = oldState.forDelivery
            )
            is ProfileEffect.UserLoadedEffect -> ProfileViewState(
                isInitialLoading = false,
                initialLoadingError = null,
                user = effect.user,
                cartInformation = oldState.cartInformation,
                forDelivery = effect.forDelivery
            )
            is ProfileEffect.CartInformationLoaded -> ProfileViewState(
                isInitialLoading = oldState.isInitialLoading,
                initialLoadingError = oldState.initialLoadingError,
                user = oldState.user,
                cartInformation = effect.cartInformation,
                forDelivery = oldState.forDelivery
            )
            ProfileEffect.NoEffect -> oldState
        }

}