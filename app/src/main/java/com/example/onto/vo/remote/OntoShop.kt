package com.example.onto.vo.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoShop(
    @field:Json(name = "shop_id")
    val id: Long,
    val name: String,
    val address: String,
    val location: OntoLocation,
    val partner: OntoPartner
)