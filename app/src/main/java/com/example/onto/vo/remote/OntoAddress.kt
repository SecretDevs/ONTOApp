package com.example.onto.vo.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoAddress(
    val city: String,
    val street: String,
    val house: String,
    val apartment: String
)