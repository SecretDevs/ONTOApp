package com.example.onto.mapper

import com.example.onto.vo.inapp.OntoProduct
import com.example.onto.vo.inapp.OntoProductWithSimilar
import com.example.onto.vo.local.LocalOntoProductWithSimilar
import com.example.onto.vo.remote.RemoteOntoProduct
import com.example.onto.vo.remote.RemoteOntoProductParameter
import javax.inject.Inject

class ProductWithSimilarMapper @Inject constructor(
    private val productMapper: ProductMapper
) : Mapper<OntoProductWithSimilar, LocalOntoProductWithSimilar, RemoteOntoProduct> {
    override fun mapFromEntity(data: LocalOntoProductWithSimilar): OntoProductWithSimilar =
        OntoProductWithSimilar(
            product = productMapper.mapFromEntity(data.product),
            similarProducts = data.similarProducts.map(productMapper::mapFromEntity)
        )

    override fun mapToEntity(data: OntoProductWithSimilar): LocalOntoProductWithSimilar =
        LocalOntoProductWithSimilar(
            product = productMapper.mapToEntity(data.product),
            similarProducts = data.similarProducts.map(productMapper::mapToEntity)
        )

    //IDs in similar is enough to create inner reference
    override fun mapFromDomain(data: RemoteOntoProduct): OntoProductWithSimilar =
        OntoProductWithSimilar(
            product = productMapper.mapFromDomain(data),
            similarProducts = data.similarProducts.map {
                OntoProduct(
                    id = it,
                    name = "",
                    price = -1F,
                    image = "",
                    info = "",
                    proteins = "",
                    fat = "",
                    carbon = "",
                    kcal = "",
                    description = "",
                    inStock = -1
                )
            }
        )

    override fun mapToDomain(data: OntoProductWithSimilar): RemoteOntoProduct =
        RemoteOntoProduct(
            id = data.product.id,
            name = data.product.name,
            price = data.product.price,
            image = data.product.image,
            info = data.product.info,
            parameters = listOf(
                RemoteOntoProductParameter(name = "белки", value = data.product.proteins),
                RemoteOntoProductParameter(name = "жиры", value = data.product.fat),
                RemoteOntoProductParameter(name = "углеводы", value = data.product.carbon),
                RemoteOntoProductParameter(name = "Ккал", value = data.product.kcal),
            ),
            description = data.product.description,
            inStock = data.product.inStock,
            similarProducts = data.similarProducts.map { it.id }
        )

}