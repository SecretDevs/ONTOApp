package com.example.onto.mapper

import com.example.onto.vo.inapp.OntoProduct
import com.example.onto.vo.local.LocalOntoProduct
import com.example.onto.vo.remote.RemoteOntoProduct
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
            description = data.description,
            isInStock = data.isInStock
        )

    override fun mapToEntity(data: OntoProduct): LocalOntoProduct =
        LocalOntoProduct(
            id = data.id,
            name = data.name,
            price = data.price,
            image = data.image,
            info = data.info,
            description = data.description,
            isInStock = data.isInStock
        )

    override fun mapFromDomain(data: RemoteOntoProduct): OntoProduct =
        OntoProduct(
            id = data.id,
            name = data.name,
            price = data.price,
            image = data.image,
            info = data.info,
            description = data.description,
            isInStock = data.isInStock
        )

    override fun mapToDomain(data: OntoProduct): RemoteOntoProduct =
        RemoteOntoProduct(
            id = data.id,
            name = data.name,
            price = data.price,
            image = data.image,
            info = data.info,
            description = data.description,
            isInStock = data.isInStock
        )

}