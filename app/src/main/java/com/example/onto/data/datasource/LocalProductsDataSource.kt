package com.example.onto.data.datasource

import com.example.onto.data.local.ProductsDao
import com.example.onto.data.local.SimilarProductsDao
import com.example.onto.utils.Result
import com.example.onto.vo.local.LocalOntoProduct
import com.example.onto.vo.local.LocalOntoProductWithSimilar
import com.example.onto.vo.local.ProductsInnerRef
import javax.inject.Inject

interface LocalProductsDataSource {
    suspend fun getProducts(): Result<List<LocalOntoProduct>>
    suspend fun getFilteredProducts(tagIds: List<Long>): Result<List<LocalOntoProduct>>
    suspend fun getProductById(productId: Long): Result<LocalOntoProductWithSimilar>
    suspend fun addProducts(products: List<LocalOntoProductWithSimilar>): Result<Boolean>
}

class LocalProductsDataSourceImpl @Inject constructor(
    private val productsDao: ProductsDao,
    private val similarProductsDao: SimilarProductsDao
) : LocalProductsDataSource {
    override suspend fun getProducts(): Result<List<LocalOntoProduct>> =
        try {
            Result.Success(
                productsDao.getProducts()
            )
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun getFilteredProducts(tagIds: List<Long>): Result<List<LocalOntoProduct>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(productId: Long): Result<LocalOntoProductWithSimilar> =
        try {
            Result.Success(
                LocalOntoProductWithSimilar(
                    product = productsDao.getProductById(productId),
                    similarProducts = productsDao.getSimilarProductsByProductId(productId)
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun addProducts(products: List<LocalOntoProductWithSimilar>): Result<Boolean> =
        try {
            similarProductsDao.insert(
                products.flatMap { parentProduct ->
                    parentProduct.similarProducts.map {
                        ProductsInnerRef(
                            productId = parentProduct.product.id,
                            similarProductId = it.id
                        )
                    }
                }
            )
            Result.Success(
                productsDao.insertProducts(
                    products.map { it.product }
                ).all { it != -1L }
            )
        } catch (e: Exception) {
            Result.Error(e)
        }

}