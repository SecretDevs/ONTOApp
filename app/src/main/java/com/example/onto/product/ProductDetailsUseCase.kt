package com.example.onto.product

import com.example.onto.products.ProductsDataSource
import com.example.onto.utils.Result
import com.example.onto.vo.OntoProduct
import javax.inject.Inject

interface ProductDetailsUseCase {
    suspend fun getProductDetails(productId: Long): Result<OntoProduct>
}

class ProductDetailsUseCaseImpl @Inject constructor(
    private val remoteDataSource: ProductsDataSource
) : ProductDetailsUseCase {

    override suspend fun getProductDetails(productId: Long): Result<OntoProduct> =
        remoteDataSource.getProductById(productId)

}