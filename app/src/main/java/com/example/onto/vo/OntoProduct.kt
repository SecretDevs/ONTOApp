package com.example.onto.vo

import com.squareup.moshi.Json

data class OntoProduct(
    @Json(name = "product_id") val id: Long,
    val name: String,
    val price: Float,
    val image: String,
    val info: String,
    val description: String,
    @Json(name = "is_in_stock") val isInStock: Boolean
)