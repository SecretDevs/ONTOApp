package com.example.onto.order.delivery

import com.example.onto.base.MviViewState

data class OrderDeliveryViewState(
    val pickupPoints: List<String>,
    val cities: List<String>,
    val userCity: String,
    val userStreet: String,
    val userHouse: String,
    val userBuilding: String,
    val userApartment: String,
) : MviViewState {
    override fun log(): String = this.toString()
}