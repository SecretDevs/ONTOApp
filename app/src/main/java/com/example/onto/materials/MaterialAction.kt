package com.example.onto.materials
import com.example.onto.base.MviAction
import com.example.onto.products.ProductsAction

sealed class MaterialAction : MviAction {

    object LoadProductsAction : MaterialAction()

    object RefreshProductsAction : MaterialAction()
}