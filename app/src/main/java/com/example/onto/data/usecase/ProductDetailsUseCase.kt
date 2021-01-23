package com.example.onto.data.usecase

import com.example.onto.data.datasource.LocalProductsDataSource
import com.example.onto.data.datasource.RemoteProductsDataSource
import com.example.onto.mapper.ProductMapper
import com.example.onto.mapper.ProductWithSimilarMapper
import com.example.onto.utils.Result
import com.example.onto.vo.inapp.OntoProductWithSimilar
import javax.inject.Inject

interface ProductDetailsUseCase {
    suspend fun getProductDetails(productId: Long): Result<OntoProductWithSimilar>
}

class ProductDetailsUseCaseImpl @Inject constructor(
    private val remoteDataSource: RemoteProductsDataSource,
    private val localDataSource: LocalProductsDataSource,
    private val productMapper: ProductMapper,
    private val productWithSimilarMapper: ProductWithSimilarMapper
) : ProductDetailsUseCase {

    override suspend fun getProductDetails(productId: Long): Result<OntoProductWithSimilar> {
        //TODO: remove comments after API update
//        when (val remoteResult = remoteDataSource.getProductById(productId)) {
//            is Result.Success -> localDataSource.addProducts(
//                listOf(
//                    productWithSimilarMapper.mapToEntity(
//                        productWithSimilarMapper.mapFromDomain(
//                            remoteResult.data
//                        )
//                    )
//                )
//            )
//            is Result.Error -> return remoteResult
//        }
        return when (val result = localDataSource.getProductById(productId)) {
            is Result.Success -> Result.Success(productWithSimilarMapper.mapFromEntity(result.data))
            is Result.Error -> Result.Error(result.throwable)
        }
    }

}