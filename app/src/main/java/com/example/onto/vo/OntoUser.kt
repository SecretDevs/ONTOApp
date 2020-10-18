package com.example.onto.vo

import com.squareup.moshi.Json

data class OntoUser(
    @Json(name = "user_id") val id: Long,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val phone: String,
    val email: String,
    val address: OntoAddress
)