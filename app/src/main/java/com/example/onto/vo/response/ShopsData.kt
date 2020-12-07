package com.example.onto.vo.response

import com.example.onto.vo.remote.OntoShop
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShopsData(val shops: List<OntoShop>)