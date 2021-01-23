package com.example.onto.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.onto.vo.local.ProductsInnerRef

@Dao
interface SimilarProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(refs: List<ProductsInnerRef>)
}