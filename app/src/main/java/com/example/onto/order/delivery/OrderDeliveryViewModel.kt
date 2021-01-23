package com.example.onto.order.delivery

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.data.usecase.OrderPrefsUseCase
import com.example.onto.data.usecase.ProfilePrefsUseCase

class OrderDeliveryViewModel @ViewModelInject constructor(
    private val orderPrefsUseCase: OrderPrefsUseCase,
    private val profilePrefsUseCase: ProfilePrefsUseCase
) : BaseViewModel<OrderDeliveryViewState, OrderDeliveryEffect, OrderDeliveryIntent, OrderDeliveryAction>() {
    override fun initialState(): OrderDeliveryViewState = OrderDeliveryViewState(
        pickupPoints = emptyList(),
        cities = emptyList(),
        userCity = "",
        userStreet = "",
        userHouse = "",
        userBuilding = "",
        userApartment = "",
    )

    override fun intentInterpreter(intent: OrderDeliveryIntent): OrderDeliveryAction =
        when (intent) {
            OrderDeliveryIntent.InitialIntent -> OrderDeliveryAction.LoadOrderAction
            is OrderDeliveryIntent.SaveOrderDeliveryIntent -> OrderDeliveryAction.SaveOrderDeliveryAction(
                deliveryType = intent.deliveryType,
                city = intent.city,
                street = intent.street,
                house = intent.house,
                building = intent.building,
                apartment = intent.apartment,
                pickup = intent.pickup,
                commentary = intent.commentary
            )
        }

    override suspend fun performAction(action: OrderDeliveryAction): OrderDeliveryEffect =
        when (action) {
            OrderDeliveryAction.LoadOrderAction -> {
                val pepega = profilePrefsUseCase.isUsingForDelivery()
                OrderDeliveryEffect.OrderLoadedEffect(
                    pickupPoints = emptyList(),
                    cities = emptyList(),
                    userCity = if (profilePrefsUseCase.isUsingForDelivery()) profilePrefsUseCase.getUserCity() else "",
                    userStreet = if (profilePrefsUseCase.isUsingForDelivery()) profilePrefsUseCase.getUserStreet() else "",
                    userHouse = if (profilePrefsUseCase.isUsingForDelivery()) profilePrefsUseCase.getUserHouse() else "",
                    userBuilding = if (profilePrefsUseCase.isUsingForDelivery()) profilePrefsUseCase.getUserBuilding() else "",
                    userApartment = if (profilePrefsUseCase.isUsingForDelivery()) profilePrefsUseCase.getUserApartment() else "",
                )
            }
            is OrderDeliveryAction.SaveOrderDeliveryAction -> {
                orderPrefsUseCase.apply {
                    setDeliveryType(action.deliveryType)
                    setUserCity(action.city)
                    setUserStreet(action.street)
                    setUserHouse(action.house)
                    setUserBuilding(action.building)
                    setUserApartment(action.apartment)
                    setPickupPoint(action.pickup)
                    setCommentary(action.commentary)
                }
                OrderDeliveryEffect.NoEffect
            }
        }

    override fun stateReducer(
        oldState: OrderDeliveryViewState,
        effect: OrderDeliveryEffect
    ): OrderDeliveryViewState =
        when (effect) {
            is OrderDeliveryEffect.OrderLoadedEffect -> OrderDeliveryViewState(
                pickupPoints = effect.pickupPoints,
                cities = effect.cities,
                userCity = effect.userCity,
                userStreet = effect.userStreet,
                userHouse = effect.userHouse,
                userBuilding = effect.userBuilding,
                userApartment = effect.userApartment,
            )
            OrderDeliveryEffect.NoEffect -> oldState
        }

}