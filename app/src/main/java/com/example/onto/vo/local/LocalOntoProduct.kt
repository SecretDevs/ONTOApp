package com.example.onto.vo.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalOntoProduct(
    @PrimaryKey val id: Long,
    val name: String,
    val price: Float,
    val image: String,
    val info: String,
    val description: String,
    @ColumnInfo(name = "is_in_stock") val isInStock: Boolean
)