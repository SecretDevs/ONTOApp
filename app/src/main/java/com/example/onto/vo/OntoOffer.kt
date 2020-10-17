package com.example.onto.vo

import com.squareup.moshi.Json

data class OntoOffer(
    @Json(name = "offer_id") val id: Long,
    val name: String,
    val price: Float,
    @Json(name = "base_price") val basePrice: Float,
    val image: String,
    @Json(name = "order_info") val orderInfo: String,
    val partner: OntoPartner,
    val description: String,
    val expiration: String
)