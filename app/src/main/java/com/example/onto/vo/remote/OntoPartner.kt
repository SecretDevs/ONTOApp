package com.example.onto.vo.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoPartner(
    @field:Json(name = "partner_id")
    val id: Long,
    val name: String,
    val logo: String
)