package com.example.onto.vo

import com.squareup.moshi.Json

data class OntoShop(
    @Json(name = "shop_id") val id: Long,
    val name: String,
    val address: String,
    val location: OntoLocation,
    val partner: OntoPartner
)