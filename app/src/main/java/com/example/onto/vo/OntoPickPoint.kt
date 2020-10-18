package com.example.onto.vo

import com.squareup.moshi.Json

data class OntoPickPoint(
    @Json(name = "point_id") val id: Long,
    val name: String,
    val address: String,
    val location: OntoLocation
)