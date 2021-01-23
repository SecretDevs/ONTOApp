package com.example.onto.data.local

import androidx.room.*
import com.example.onto.vo.local.LocalOntoProduct

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<LocalOntoProduct>): List<Long>

    @Query("SELECT * FROM LocalOntoProduct")
    fun getProducts(): List<LocalOntoProduct>

    @Query("SELECT * FROM LocalOntoProduct WHERE id = :productId")
    fun getProductById(productId: Long): LocalOntoProduct

    @Query("SELECT Similar.* FROM LocalOntoProduct as Product " +
            "INNER JOIN ProductsInnerRef as Ref ON Product.id = Ref.product_id " +
            "INNER JOIN LocalOntoProduct as Similar ON Ref.similar_product_id = Similar.id " +
            "WHERE Product.id = :productId")
    fun getSimilarProductsByProductId(productId: Long): List<LocalOntoProduct>
}