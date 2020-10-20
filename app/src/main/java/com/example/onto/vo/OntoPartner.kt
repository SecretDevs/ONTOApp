package com.example.onto.vo

import androidx.room.ColumnInfo
import com.squareup.moshi.Json

data class OntoPartner(
    @Json(name = "partner_id")
    @ColumnInfo(name = "partner_id")
    val id: Long,
    @ColumnInfo(name = "partner_name")
    val name: String,
    val logo: String
)