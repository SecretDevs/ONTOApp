package com.example.onto.vo.response

import com.example.onto.vo.OntoPickPoint
import com.example.onto.vo.OntoProduct
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PointsData(val points: MutableList<OntoPickPoint>)