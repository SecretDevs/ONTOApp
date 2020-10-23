package com.example.onto.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoProduct(
    @field:Json(name = "product_id") val id: Long,
    val name: String,
    val price: Float,
    val image: String,
    val info: String,
    val description: String,
    @field:Json(name = "is_in_stock") val isInStock: Boolean
)