package com.example.onto.discount
import com.example.onto.base.MviAction

sealed class DiscountAction : MviAction {

    object LoadProductsAction : DiscountAction()

    object RefreshProductsAction : DiscountAction()
}