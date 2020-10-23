package com.example.onto.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoLocation(
    @field:Json(name = "lat") val latitude: Float,
    @field:Json(name = "long") val longitude: Float
){
    override fun toString(): String {
        return "${this.latitude} ${this.longitude}"
    }
}