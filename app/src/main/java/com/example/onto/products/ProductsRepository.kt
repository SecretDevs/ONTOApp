package com.example.onto.products

import com.example.onto.data.remote.OntoApiService
import com.example.onto.vo.OntoProduct
import com.example.onto.utils.Result
import javax.inject.Inject

interface ProductsRepository {
    suspend fun getProducts(): Result<List<OntoProduct>>
    suspend fun getFilteredProducts(tagIds: List<Long>): Result<List<OntoProduct>>
    suspend fun getProductById(productId: Long): Result<OntoProduct>
}

class RemoteProductsRepository @Inject constructor(
    private val service: OntoApiService
) : ProductsRepository {
    override suspend fun getProducts(): Result<List<OntoProduct>> {
        val response = service.getProducts()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.data.products)
        } else {
            Result.Error(Throwable(response.message()))
        }
    }

    override suspend fun getFilteredProducts(tagIds: List<Long>): Result<List<OntoProduct>> {
        throw UnsupportedOperationException("API is not realized yet")
    }

    override suspend fun getProductById(productId: Long): Result<OntoProduct> {
        val response = service.getProductById(productId)
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.data)
        } else {
            Result.Error(Throwable(response.message()))
        }
    }

}