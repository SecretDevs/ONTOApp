package com.example.onto.order.delivery

import com.example.onto.base.MviEffect

sealed class OrderDeliveryEffect : MviEffect {

    data class OrderLoadedEffect(
        val pickupPoints: List<String>,
        val cities: List<String>,
        val userCity: String,
        val userStreet: String,
        val userHouse: String,
        val userBuilding: String,
        val userApartment: String,
    ) : OrderDeliveryEffect()

    object NoEffect : OrderDeliveryEffect()

}