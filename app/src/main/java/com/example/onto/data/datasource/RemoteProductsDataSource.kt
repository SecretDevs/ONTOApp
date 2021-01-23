package com.example.onto.data.datasource

import com.example.onto.data.local.ProductsDao
import com.example.onto.data.local.SimilarProductsDao
import com.example.onto.data.remote.OntoApiService
import com.example.onto.utils.Result
import com.example.onto.vo.local.LocalOntoProduct
import com.example.onto.vo.local.LocalOntoProductWithSimilar
import com.example.onto.vo.local.ProductsInnerRef
import com.example.onto.vo.remote.RemoteOntoProduct
import timber.log.Timber
import javax.inject.Inject

interface RemoteProductsDataSource {
    suspend fun getProducts(): Result<List<RemoteOntoProduct>>
    suspend fun getFilteredProducts(tagIds: List<Long>): Result<List<RemoteOntoProduct>>
    suspend fun getProductById(productId: Long): Result<RemoteOntoProduct>
}

class RemoteProductsDataSourceImpl @Inject constructor(
    private val service: OntoApiService,
) : RemoteProductsDataSource {
    override suspend fun getProducts(): Result<List<RemoteOntoProduct>> {
        return try {
            val response = service.getProducts()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(
                    response.body()!!.data.products
                )
            } else {
                Timber.e(response.message())
                Result.Error(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun getFilteredProducts(tagIds: List<Long>): Result<List<RemoteOntoProduct>> {
        throw UnsupportedOperationException("API is not realized yet")
    }

    override suspend fun getProductById(productId: Long): Result<RemoteOntoProduct> {
        return try {
            val response = service.getProductById()
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