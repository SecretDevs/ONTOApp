package com.example.onto.data.usecase

import com.example.onto.data.datasource.ProductsDataSource
import com.example.onto.di.LocalDataSource
import com.example.onto.di.RemoteDataSource
import com.example.onto.mapper.ProductMapper
import com.example.onto.utils.Result
import com.example.onto.vo.inapp.OntoProduct
import javax.inject.Inject

interface ProductDetailsUseCase {
    suspend fun getProductDetails(productId: Long): Result<OntoProduct>
}

class ProductDetailsUseCaseImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: ProductsDataSource,
    @LocalDataSource private val localDataSource: ProductsDataSource
) : ProductDetailsUseCase {

    override suspend fun getProductDetails(productId: Long): Result<OntoProduct> {
        when (val remoteResult = remoteDataSource.getProductById(productId)) {
            is Result.Success -> localDataSource.addProducts(listOf(remoteResult.data))
            is Result.Error -> return remoteResult
        }
        return localDataSource.getProductById(productId)
    }

}