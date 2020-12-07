package com.example.onto.material

import com.example.onto.base.MviViewState
import com.example.onto.vo.remote.OntoArticle

data class MaterialDetailsViewState(
    val isInitialLoading: Boolean = false,
    val initialLoadingError: Throwable? = null,
    val article: OntoArticle? = null
) : MviViewState {
    override fun log(): String = this.toString()
}