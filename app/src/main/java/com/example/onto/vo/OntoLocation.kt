package com.example.onto.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoLocation(
    @field:Json(name = "lat") val latitude: Double,
    @field:Json(name = "long") val longitude: Double
)