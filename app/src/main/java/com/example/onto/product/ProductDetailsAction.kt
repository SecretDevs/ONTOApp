package com.example.onto.product

import com.example.onto.base.MviAction

sealed class ProductDetailsAction : MviAction {

    data class LoadProductDetailsAction(
        val productId: Long
    ) : ProductDetailsAction()

}