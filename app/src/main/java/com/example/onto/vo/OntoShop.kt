package com.example.onto.vo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "shops")
data class OntoShop(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "shop_id")
    @ColumnInfo(name = "shop_id")
    val id: Long,
    @ColumnInfo(name = "shop_name")
    val name: String,
    val address: String,
    @Embedded
    val location: OntoLocation,
    @Embedded
    val partner: OntoPartner
)