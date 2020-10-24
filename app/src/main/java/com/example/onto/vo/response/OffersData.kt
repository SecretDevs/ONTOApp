package com.example.onto.vo.response

import com.example.onto.vo.OntoOffer
import com.example.onto.vo.OntoProduct
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OffersData(val offers: MutableList<OntoOffer>)