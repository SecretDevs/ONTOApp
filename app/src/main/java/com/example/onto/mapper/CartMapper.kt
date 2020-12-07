package com.example.onto.mapper

import com.example.onto.vo.inapp.CartItem
import com.example.onto.vo.local.LocalCartItem
import com.example.onto.vo.local.LocalCartItemWithProduct
import javax.inject.Inject

class CartMapper @Inject constructor(
    private val productMapper: ProductMapper
) : Mapper<CartItem, LocalCartItemWithProduct, Nothing> {
    override fun mapFromEntity(data: LocalCartItemWithProduct): CartItem =
        CartItem(
            product = productMapper.mapFromEntity(data.product),
            quantity = data.item.quantity
        )

    override fun mapToEntity(data: CartItem): LocalCartItemWithProduct =
        LocalCartItemWithProduct(
            item = LocalCartItem(
                productId = data.product.id,
                quantity = data.quantity
            ),
            product = productMapper.mapToEntity(data = data.product)
        )

    override fun mapFromDomain(data: Nothing): CartItem {
        TODO("Not yet implemented")
    }

    override fun mapToDomain(data: CartItem): Nothing {
        TODO("Not yet implemented")
    }

}