package com.example.onto.products

import com.example.onto.utils.Result
import com.example.onto.vo.OntoProduct

interface ProductsUseCase {
    suspend fun getProductTags(): Result<List<Int>>
    suspend fun getProducts(tagIds: List<Long>): Result<List<OntoProduct>>
    suspend fun getCartPrice(): Result<Float>
}

class ProductsUseCaseImpl(
    private val remoteProductsRepository: ProductsRepository
) : ProductsUseCase {
    override suspend fun getProductTags(): Result<List<Int>> {
        throw UnsupportedOperationException("API is not realized yet")
    }

    override suspend fun getProducts(tagIds: List<Long>): Result<List<OntoProduct>> =
        if (tagIds.isEmpty()) remoteProductsRepository.getProducts()
        else remoteProductsRepository.getFilteredProducts(tagIds)

    override suspend fun getCartPrice(): Result<Float> {
        throw UnsupportedOperationException("API is not realized yet")
    }

}