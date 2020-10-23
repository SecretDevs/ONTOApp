package com.example.onto.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoPickPoint(
    @field:Json(name = "point_id") val id: Long,
    val name: String,
    val address: String,
    val location: OntoLocation
)