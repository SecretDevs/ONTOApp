package com.example.onto.material

import com.example.onto.base.MviAction

sealed class MaterialDetailsAction : MviAction {

    data class LoadProductDetailsAction(
        val articleId: Long
    ) : MaterialDetailsAction()

}