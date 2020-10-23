package com.example.onto.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OntoResponse(@field:Json(name = "data")val data: ShopsData)