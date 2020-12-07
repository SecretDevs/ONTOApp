package com.example.onto.data.datasource

import com.example.onto.data.local.CartDao
import com.example.onto.mapper.CartMapper
import com.example.onto.utils.Result
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.inapp.CartItem
import com.example.onto.vo.local.LocalCartItem
import javax.inject.Inject

interface CartDataSource {
    suspend fun addNewCartItem(productId: Long, quantity: Int): Result<Boolean>
    suspend fun getCartInformation(): Result<CartInformation>
    suspend fun getCartProducts(): Result<List<CartItem>>

    /**
     * quantityChangeMode: 1 - add one, -1 - remove one, 0 - remove item
     * return: updated list of items
     */
    suspend fun updateCartItem(cartProductId: Long, quantityChangeMode: Int): Result<List<CartItem>>
}

class LocalCartDataSource @Inject constructor(
    private val cartDao: CartDao,
    private val cartMapper: CartMapper
) : CartDataSource {
    override suspend fun addNewCartItem(productId: Long, quantity: Int): Result<Boolean> =
        try {
            val cartItem = LocalCartItem(productId = productId, quantity = quantity)
            Result.Success(cartDao.insertCartItem(cartItem) != -1L)
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun getCartInformation(): Result<CartInformation> =
        try {
            Result.Success(cartDao.getCartInformation())
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun getCartProducts(): Result<List<CartItem>> =
        try {
            Result.Success(cartDao.getCartItems().map(cartMapper::mapFromEntity))
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun updateCartItem(
        cartProductId: Long,
        quantityChangeMode: Int
    ): Result<List<CartItem>> =
        try {
            val cartItem = cartDao.getLocalCartItemById(cartProductId)
            when (quantityChangeMode) {
                0 -> cartDao.deleteCartItem(
                    cartItem
                )
                else -> cartDao.updateCartItem(
                    LocalCartItem(
                        id = cartItem.id,
                        productId = cartItem.productId,
                        quantity = cartItem.quantity + quantityChangeMode
                    )
                )
            }
            getCartProducts()
        } catch (e: Exception) {
            Result.Error(e)
        }

}