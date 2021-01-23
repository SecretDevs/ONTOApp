package com.example.onto.vo.local

import androidx.room.*

@Entity(indices = [Index(value = ["product_id", "similar_product_id"], unique = true)])
data class ProductsInnerRef(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "similar_product_id") val similarProductId: Long
)

data class LocalOntoProductWithSimilar(
    val product: LocalOntoProduct,
    val similarProducts: List<LocalOntoProduct>
)