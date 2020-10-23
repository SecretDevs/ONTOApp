package com.example.onto.vo.response

import com.example.onto.vo.OntoShop
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShopsData(val shops: List<OntoShop>)