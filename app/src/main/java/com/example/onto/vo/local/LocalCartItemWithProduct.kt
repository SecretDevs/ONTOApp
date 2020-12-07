package com.example.onto.vo.local

import androidx.room.*

data class LocalCartItemWithProduct(
    @Embedded var item: LocalCartItem,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "id"
    )
    var product: LocalOntoProduct
)

@Entity(indices = [Index(value = ["product_id"], unique = true)])
data class LocalCartItem(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "product_id") val productId: Long,
    val quantity: Int
)