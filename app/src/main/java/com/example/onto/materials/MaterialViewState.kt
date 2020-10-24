package com.example.onto.materials

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoArticle

data class MaterialViewState (
    val materials: List<OntoArticle> = emptyList(),
    val isRefreshing: Boolean = false
) : MviViewState