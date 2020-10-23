package com.example.onto.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoPartner(
    @field:Json(name = "partner_id")
    val id: Long,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "logo")
    val logo: String
)