package com.example.onto.data.usecase

import com.example.onto.data.datasource.LocalProductsDataSource
import com.example.onto.data.datasource.RemoteProductsDataSource
import com.example.onto.mapper.ProductMapper
import com.example.onto.mapper.ProductWithSimilarMapper
import com.example.onto.utils.Result
import com.example.onto.vo.inapp.OntoProduct
import javax.inject.Inject

interface ProductsUseCase {
    suspend fun getProductTags(): Result<List<Int>>
    suspend fun getProducts(tagIds: List<Long>): Result<List<OntoProduct>>
}

class ProductsUseCaseImpl @Inject constructor(
    private val remoteProductsDataSource: RemoteProductsDataSource,
    private val localDataSource: LocalProductsDataSource,
    private val productMapper: ProductMapper,
    private val productWithSimilarMapper: ProductWithSimilarMapper
) : ProductsUseCase {
    override suspend fun getProductTags(): Result<List<Int>> {
        throw UnsupportedOperationException("API is not realized yet")
    }

    override suspend fun getProducts(tagIds: List<Long>): Result<List<OntoProduct>> {
        val remoteResult =
            if (tagIds.isEmpty()) remoteProductsDataSource.getProducts()
            else remoteProductsDataSource.getFilteredProducts(tagIds)
        when (remoteResult) {
            is Result.Success -> localDataSource.addProducts(
                remoteResult.data
                    .map(productWithSimilarMapper::mapFromDomain)
                    .map(productWithSimilarMapper::mapToEntity)
            )
            is Result.Error -> return remoteResult
        }

        return if (tagIds.isEmpty()) {
            when (val result = localDataSource.getProducts()) {
                is Result.Success -> Result.Success(result.data.map(productMapper::mapFromEntity))
                is Result.Error -> Result.Error(result.throwable)
            }
        } else {
            when (val result = localDataSource.getFilteredProducts(tagIds)) {
                is Result.Success -> Result.Success(result.data.map(productMapper::mapFromEntity))
                is Result.Error -> Result.Error(result.throwable)
            }
        }
    }

}