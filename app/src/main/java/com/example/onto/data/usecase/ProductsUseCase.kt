package com.example.onto.data.usecase

import com.example.onto.data.datasource.ProductsDataSource
import com.example.onto.di.LocalDataSource
import com.example.onto.di.RemoteDataSource
import com.example.onto.utils.Result
import com.example.onto.vo.inapp.OntoProduct
import javax.inject.Inject

interface ProductsUseCase {
    suspend fun getProductTags(): Result<List<Int>>
    suspend fun getProducts(tagIds: List<Long>): Result<List<OntoProduct>>
}

class ProductsUseCaseImpl @Inject constructor(
    @RemoteDataSource private val remoteProductsDataSource: ProductsDataSource,
    @LocalDataSource private val localDataSource: ProductsDataSource
) : ProductsUseCase {
    override suspend fun getProductTags(): Result<List<Int>> {
        throw UnsupportedOperationException("API is not realized yet")
    }

    override suspend fun getProducts(tagIds: List<Long>): Result<List<OntoProduct>> {
        val remoteResult =
            if (tagIds.isEmpty()) remoteProductsDataSource.getProducts()
            else remoteProductsDataSource.getFilteredProducts(tagIds)
        when (remoteResult) {
            is Result.Success -> localDataSource.addProducts(remoteResult.data)
            is Result.Error -> return remoteResult
        }
        return if (tagIds.isEmpty()) localDataSource.getProducts() else localDataSource.getFilteredProducts(
            tagIds
        )
    }

}