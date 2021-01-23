package com.example.onto.order.confirmation

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.CartUseCase
import com.example.onto.data.usecase.OrderPrefsUseCase
import com.example.onto.utils.Result

class OrderConfirmationViewModel @ViewModelInject constructor(
    private val orderPrefsUseCase: OrderPrefsUseCase,
    private val cartUseCase: CartUseCase
) : BaseViewModel<OrderConfirmationViewState, OrderConfirmationEffect, OrderConfirmationIntent, OrderConfirmationAction>() {
    override fun initialState(): OrderConfirmationViewState = OrderConfirmationViewState(
        orderNumber = -1L,
        items = emptyList(),
        firstName = "",
        lastName = "",
        email = "",
        phone = "",
        deliveryType = 3,
        deliveryAddress = listOf(""),
        commentary = ""
    )

    override fun intentInterpreter(intent: OrderConfirmationIntent): OrderConfirmationAction =
        when (intent) {
            OrderConfirmationIntent.InitialIntent -> OrderConfirmationAction.LoadOrderValuesAction
            OrderConfirmationIntent.OrderConfirmationNothingIntent -> throw IllegalArgumentException(
                "Nothing intent interpreting"
            )
        }

    override suspend fun performAction(action: OrderConfirmationAction): OrderConfirmationEffect =
        when (action) {
            OrderConfirmationAction.LoadOrderValuesAction -> {
                val result = cartUseCase.getCartProducts()
                OrderConfirmationEffect.OrderValuesLoaded(
                    orderNumber = 0L, //TODO:????? HOW
                    items = if (result is Result.Success) result.data else emptyList(),
                    firstName = orderPrefsUseCase.getFirstName(),
                    lastName = orderPrefsUseCase.getLastName(),
                    email = orderPrefsUseCase.getEmail(),
                    phone = orderPrefsUseCase.getPhone(),
                    deliveryType = orderPrefsUseCase.getDeliveryType(),
                    deliveryAddress = if (orderPrefsUseCase.getDeliveryType() == 3) listOf(
                        orderPrefsUseCase.getPickupPoint()
                    ) else listOf(
                        orderPrefsUseCase.getUserCity(),
                        orderPrefsUseCase.getUserStreet(),
                        orderPrefsUseCase.getUserHouse(),
                        orderPrefsUseCase.getUserBuilding(),
                        orderPrefsUseCase.getUserApartment()
                    ),
                    commentary = orderPrefsUseCase.getCommentary()
                )
            }
        }

    override fun stateReducer(
        oldState: OrderConfirmationViewState,
        effect: OrderConfirmationEffect
    ): OrderConfirmationViewState =
        when (effect) {
            is OrderConfirmationEffect.OrderValuesLoaded -> OrderConfirmationViewState(
                orderNumber = effect.orderNumber,
                items = effect.items,
                firstName = effect.firstName,
                lastName = effect.lastName,
                email = effect.email,
                phone = effect.phone,
                deliveryType = effect.deliveryType,
                deliveryAddress = effect.deliveryAddress,
                commentary = effect.commentary
            )
        }

}