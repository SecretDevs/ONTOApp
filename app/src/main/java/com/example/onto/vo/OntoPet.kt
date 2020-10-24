package com.example.onto.vo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoPet(
    val type: String,
    val name: String
)