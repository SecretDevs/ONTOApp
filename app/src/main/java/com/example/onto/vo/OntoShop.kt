package com.example.onto.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoShop(
    @field:Json(name = "shop_id")
    val id: Long,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "address")
    val address: String,
    @field:Json(name = "location")
    val location: OntoLocation,
    @field:Json(name = "partner")
    val partner: OntoPartner
)