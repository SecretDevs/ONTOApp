package com.example.onto.material

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoArticle

data class MaterialDetailsViewState(
    val isInitialLoading: Boolean = false,
    val initialLoadingError: Throwable? = null,
    val article: OntoArticle? = null
) : MviViewState