package com.example.onto.data.local

import androidx.room.*
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.local.LocalCartItem
import com.example.onto.vo.local.LocalCartItemWithProduct

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCartItem(cartItem: LocalCartItem): Long

    @Update
    suspend fun updateCartItem(cartItems: LocalCartItem): Int

    @Delete
    suspend fun deleteCartItem(cartItems: LocalCartItem): Int

    @Transaction
    @Query("SELECT * FROM LocalCartItem")
    suspend fun getCartItems(): List<LocalCartItemWithProduct>

    @Query("SELECT * FROM LocalCartItem WHERE product_id = :cartProductId")
    suspend fun getLocalCartItemById(cartProductId: Long): LocalCartItem

    @Query(
        "SELECT COUNT(*) AS count, SUM(LocalOntoProduct.price) as totalPrice FROM LocalCartItem " +
                "INNER JOIN LocalOntoProduct ON LocalCartItem.product_id = LocalOntoProduct.id"
    )
    suspend fun getCartInformation(): CartInformation

}