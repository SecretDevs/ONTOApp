package com.example.onto.mapper

import com.example.onto.vo.inapp.OntoProduct
import com.example.onto.vo.local.LocalOntoProduct
import com.example.onto.vo.remote.RemoteOntoProduct
import com.example.onto.vo.remote.RemoteOntoProductParameter
import javax.inject.Inject

class ProductMapper @Inject constructor(

) : Mapper<OntoProduct, LocalOntoProduct, RemoteOntoProduct> {
    override fun mapFromEntity(data: LocalOntoProduct): OntoProduct =
        OntoProduct(
            id = data.id,
            name = data.name,
            price = data.price,
            image = data.image,
            info = data.info,
            proteins = data.proteins,
            fat = data.fat,
            carbon = data.carbon,
            kcal = data.kcal,
            description = data.description,
            inStock = data.inStock
        )

    override fun mapToEntity(data: OntoProduct): LocalOntoProduct =
        LocalOntoProduct(
            id = data.id,
            name = data.name,
            price = data.price,
            image = data.image,
            info = data.info,
            proteins = data.proteins,
            fat = data.fat,
            carbon = data.carbon,
            kcal = data.kcal,
            description = data.description,
            inStock = data.inStock
        )

    override fun mapFromDomain(data: RemoteOntoProduct): OntoProduct =
        OntoProduct(
            id = data.id,
            name = data.name,
            price = data.price,
            image = data.image,
            info = data.info,
            proteins = data.parameters.firstOrNull { it.name == "белки" }?.value ?: "",
            fat = data.parameters.firstOrNull { it.name == "жиры" }?.value ?: "",
            carbon = data.parameters.firstOrNull { it.name == "углеводы" }?.value ?: "",
            kcal = data.parameters.firstOrNull { it.name == "Ккал" }?.value ?: "",
            description = data.description,
            inStock = data.inStock
        )

    override fun mapToDomain(data: OntoProduct): RemoteOntoProduct =
        RemoteOntoProduct(
            id = data.id,
            name = data.name,
            price = data.price,
            image = data.image,
            info = data.info,
            parameters = listOf(
                RemoteOntoProductParameter(name = "белки", value = data.proteins),
                RemoteOntoProductParameter(name = "жиры", value = data.fat),
                RemoteOntoProductParameter(name = "углеводы", value = data.carbon),
                RemoteOntoProductParameter(name = "Ккал", value = data.kcal),
            ),
            description = data.description,
            inStock = data.inStock,
            similarProducts = emptyList()
        )

}