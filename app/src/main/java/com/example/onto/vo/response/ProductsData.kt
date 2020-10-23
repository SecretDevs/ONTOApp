package com.example.onto.vo.response

import com.example.onto.vo.OntoProduct
import com.example.onto.vo.OntoShop
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductsData(val products: MutableList<OntoProduct>)