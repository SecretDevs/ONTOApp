package com.example.onto.product

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoProduct
import com.example.onto.vo.OntoUser

data class ProductDetailsViewState(
    val isInitialLoading: Boolean = false,
    val initialLoadingError: Throwable? = null,
    val product: OntoProduct? = null
) : MviViewState