package com.example.onto.vo

import com.squareup.moshi.Json

data class OntoLocation(
    @Json(name = "lat") val latitude: Float,
    @Json(name = "long") val longitude: Float
)