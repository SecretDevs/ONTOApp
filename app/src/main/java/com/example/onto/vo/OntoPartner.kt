package com.example.onto.vo

import com.squareup.moshi.Json

data class OntoPartner(
    @Json(name = "partner_id") val id: Long,
    val name: String,
    val logo: String
)