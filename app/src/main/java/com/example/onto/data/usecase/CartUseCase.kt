package com.example.onto.data.usecase

import com.example.onto.data.datasource.CartDataSource
import com.example.onto.di.LocalDataSource
import com.example.onto.utils.Result
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.inapp.CartItem
import javax.inject.Inject

interface CartUseCase {
    suspend fun addCartItem(productId: Long, quantity: Int): Result<Boolean>
    suspend fun getCartInformation(): Result<CartInformation>
    suspend fun getCartProducts(): Result<List<CartItem>>
    suspend fun updateCartItem(productId: Long, mode: Int): Result<List<CartItem>>
}

class CartUseCaseImpl @Inject constructor(
    @LocalDataSource private val localCartDataSource: CartDataSource
) : CartUseCase {
    override suspend fun addCartItem(productId: Long, quantity: Int): Result<Boolean> =
        localCartDataSource.addNewCartItem(productId, quantity)

    override suspend fun getCartInformation(): Result<CartInformation> =
        localCartDataSource.getCartInformation()

    override suspend fun getCartProducts(): Result<List<CartItem>> =
        localCartDataSource.getCartProducts()

    override suspend fun updateCartItem(productId: Long, mode: Int): Result<List<CartItem>> =
        localCartDataSource.updateCartItem(productId, mode)

}