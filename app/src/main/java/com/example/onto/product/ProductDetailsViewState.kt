package com.example.onto.product

import com.example.onto.base.MviViewState
import com.example.onto.vo.inapp.CartInformation
import com.example.onto.vo.inapp.OntoProduct
import com.example.onto.vo.inapp.OntoProductWithSimilar

data class ProductDetailsViewState(
    val isInitialLoading: Boolean = false,
    val initialLoadingError: Throwable? = null,
    val product: OntoProductWithSimilar? = null,
    val quantity: Int = 0,
    val cartInformation: CartInformation? = null
) : MviViewState {

    override fun log(): String = this.toString()

}