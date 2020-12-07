package com.example.onto.data.datasource

import com.example.onto.data.local.ProductsDao
import com.example.onto.data.remote.OntoApiService
import com.example.onto.mapper.ProductMapper
import com.example.onto.utils.Result
import com.example.onto.vo.inapp.OntoProduct
import timber.log.Timber
import javax.inject.Inject

interface ProductsDataSource {
    suspend fun getProducts(): Result<List<OntoProduct>>
    suspend fun getFilteredProducts(tagIds: List<Long>): Result<List<OntoProduct>>
    suspend fun getProductById(productId: Long): Result<OntoProduct>
    suspend fun addProducts(products: List<OntoProduct>): Result<Boolean>
}

class RemoteProductsDataSource @Inject constructor(
    private val service: OntoApiService,
    private val productMapper: ProductMapper
) : ProductsDataSource {
    override suspend fun getProducts(): Result<List<OntoProduct>> {
        return try {
            val response = service.getProducts()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(
                    response.body()!!.data.products
                        .map(productMapper::mapFromDomain)
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

    override suspend fun getFilteredProducts(tagIds: List<Long>): Result<List<OntoProduct>> {
        throw UnsupportedOperationException("API is not realized yet")
    }

    override suspend fun getProductById(productId: Long): Result<OntoProduct> {
        return try {
            val response = service.getProductById()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(productMapper.mapFromDomain(response.body()!!.data))
            } else {
                Timber.e(response.message())
                Result.Error(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(e)
        }
    }

    override suspend fun addProducts(products: List<OntoProduct>): Result<Boolean> =
        throw UnsupportedOperationException("Cannot cache into remote data source")
}

class LocalProductsDataSource @Inject constructor(
    private val productsDao: ProductsDao,
    private val productMapper: ProductMapper
) : ProductsDataSource {
    override suspend fun getProducts(): Result<List<OntoProduct>> =
        try {
            Result.Success(
                productsDao.getProducts()
                    .map(productMapper::mapFromEntity)
            )
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun getFilteredProducts(tagIds: List<Long>): Result<List<OntoProduct>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(productId: Long): Result<OntoProduct> =
        try {
            Result.Success(
                productMapper.mapFromEntity(productsDao.getProductById(productId))
            )
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun addProducts(products: List<OntoProduct>): Result<Boolean> =
        try {
            Result.Success(
                productsDao.insertProducts(products.map(productMapper::mapToEntity))
                    .all { it != -1L }
            )
        } catch (e: Exception) {
            Result.Error(e)
        }

}