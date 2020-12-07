package com.example.onto.vo.response

import com.example.onto.vo.remote.OntoOffer
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OffersData(val offers: MutableList<OntoOffer>)