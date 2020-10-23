package com.example.onto.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoUser(
    @field:Json(name = "user_id") val id: Long,
    @field:Json(name = "first_name") val firstName: String,
    @field:Json(name = "last_name") val lastName: String,
    val phone: String,
    val email: String,
    val address: OntoAddress
)