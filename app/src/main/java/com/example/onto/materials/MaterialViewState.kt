package com.example.onto.materials

import com.example.onto.base.MviViewState
import com.example.onto.vo.OntoArticle
import com.example.onto.vo.OntoProduct

data class MaterialViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val products: List<OntoArticle>,
    val isRefreshLoading: Boolean,
    val refreshError: Throwable?
) : MviViewState {
    companion object {
        val initialState = MaterialViewState(
            isInitialLoading = false,
            initialError = null,
            products = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        val initialLoadingState = MaterialViewState(
            isInitialLoading = true,
            initialError = null,
            products = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        fun initialErrorState(error: Throwable): MaterialViewState = MaterialViewState(
            isInitialLoading = false,
            initialError = error,
            products = emptyList(),
            isRefreshLoading = false,
            refreshError = null
        )

        fun productsLoadedState(products: List<OntoArticle>): MaterialViewState = MaterialViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            isRefreshLoading = false,
            refreshError = null
        )

        fun refreshLoadingState(products: List<OntoArticle>): MaterialViewState = MaterialViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            isRefreshLoading = true,
            refreshError = null
        )

        fun refreshLoadingErrorState(
            products: List<OntoArticle>,
            error: Throwable
        ): MaterialViewState = MaterialViewState(
            isInitialLoading = false,
            initialError = null,
            products = products,
            isRefreshLoading = false,
            refreshError = error
        )

    }
}