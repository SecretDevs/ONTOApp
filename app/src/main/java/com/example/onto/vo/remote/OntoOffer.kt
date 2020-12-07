package com.example.onto.vo.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoOffer(
    @field:Json(name = "offer_id") val id: Long,
    val name: String,
    val price: Float,
    @field:Json(name = "base_price") val basePrice: Float,
    val image: String,
    @field:Json(name = "offer_info") val offerInfo: String,
    val partner: OntoPartner,
    val description: String,
    val expiration: String
)