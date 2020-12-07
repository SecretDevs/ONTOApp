package com.example.onto.vo.response

import com.example.onto.vo.remote.OntoPickPoint
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PointsData(val points: MutableList<OntoPickPoint>)