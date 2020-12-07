package com.example.onto.vo.response

import com.example.onto.vo.remote.RemoteOntoProduct
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductsData(val products: List<RemoteOntoProduct>)