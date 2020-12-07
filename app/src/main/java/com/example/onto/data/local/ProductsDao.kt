package com.example.onto.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onto.vo.local.LocalOntoProduct

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<LocalOntoProduct>): List<Long>

    @Query("SELECT * FROM LocalOntoProduct")
    fun getProducts(): List<LocalOntoProduct>

    @Query("SELECT * FROM LocalOntoProduct WHERE id = :productId")
    fun getProductById(productId: Long): LocalOntoProduct
}