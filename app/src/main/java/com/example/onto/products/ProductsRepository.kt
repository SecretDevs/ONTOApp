package com.example.onto.products

import com.example.onto.data.remote.OntoApiService
import com.example.onto.utils.Result
import com.example.onto.vo.OntoProduct
import timber.log.Timber
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
        return try {
            val response = service.getProducts()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.data.products)
            } else {
                Timber.e(response.message())
                Result.Error(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun getFilteredProducts(tagIds: List<Long>): Result<List<OntoProduct>> {
        throw UnsupportedOperationException("API is not realized yet")
    }

    override suspend fun getProductById(productId: Long): Result<OntoProduct> {
        return try {
            val response = service.getProductById(productId)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.data)
            } else {
                Timber.e(response.message())
                Result.Error(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }
}