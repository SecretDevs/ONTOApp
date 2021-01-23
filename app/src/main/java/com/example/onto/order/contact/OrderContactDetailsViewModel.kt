package com.example.onto.order.contact

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.OrderPrefsUseCase
import com.example.onto.data.usecase.ProfilePrefsUseCase

class OrderContactDetailsViewModel @ViewModelInject constructor(
    private val orderPrefsUseCase: OrderPrefsUseCase,
    private val profilePrefsUseCase: ProfilePrefsUseCase
) : BaseViewModel<OrderContactDetailsViewState, OrderContactDetailsEffect, OrderContactDetailsIntent, OrderContactDetailsAction>() {
    override fun initialState(): OrderContactDetailsViewState = OrderContactDetailsViewState(
        firstName = "",
        lastName = "",
        email = "",
        phone = ""
    )

    override fun intentInterpreter(intent: OrderContactDetailsIntent): OrderContactDetailsAction =
        when (intent) {
            OrderContactDetailsIntent.InitialIntent -> OrderContactDetailsAction.LoadPrefValuesAction
            is OrderContactDetailsIntent.SaveOrderContactDetailsIntent -> OrderContactDetailsAction.SaveOrderContactDetailsAction(
                firstName = intent.firstName,
                lastName = intent.lastName,
                email = intent.email,
                phone = intent.phone
            )
        }

    override suspend fun performAction(action: OrderContactDetailsAction): OrderContactDetailsEffect =
        when (action) {
            OrderContactDetailsAction.LoadPrefValuesAction -> OrderContactDetailsEffect.PrefValuesLoadedEffect(
                firstName = profilePrefsUseCase.getFirstName(),
                lastName = profilePrefsUseCase.getLastName(),
                email = profilePrefsUseCase.getEmail(),
                phone = profilePrefsUseCase.getPhone()
            )
            is OrderContactDetailsAction.SaveOrderContactDetailsAction -> {
                orderPrefsUseCase.apply {
                    setFirstName(action.firstName)
                    setLastName(action.lastName)
                    setEmail(action.email)
                    setPhone(action.phone)
                }
                OrderContactDetailsEffect.NoChangeEffect
            }
        }

    override fun stateReducer(
        oldState: OrderContactDetailsViewState,
        effect: OrderContactDetailsEffect
    ): OrderContactDetailsViewState =
        when (effect) {
            is OrderContactDetailsEffect.PrefValuesLoadedEffect -> OrderContactDetailsViewState(
                firstName = effect.firstName,
                lastName = effect.lastName,
                email = effect.email,
                phone = effect.phone
            )
            OrderContactDetailsEffect.NoChangeEffect -> oldState
        }

}