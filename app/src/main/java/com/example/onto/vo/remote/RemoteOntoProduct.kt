package com.example.onto.vo.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteOntoProduct(
    @field:Json(name = "product_id") val id: Long,
    val name: String,
    val price: Float,
    val image: String,
    val info: String,
    val parameters: List<RemoteOntoProductParameter>,
    val description: String,
    @field:Json(name = "in_stock") val inStock: Int,
    @field:Json(name = "similar_products") val similarProducts: List<Long>
)