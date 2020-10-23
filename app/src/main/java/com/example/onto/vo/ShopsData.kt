package com.example.onto.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShopsData(@field:Json(name = "shops")val shops: List<OntoShop>)