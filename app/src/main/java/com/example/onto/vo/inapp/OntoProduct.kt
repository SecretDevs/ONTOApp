package com.example.onto.vo.inapp

data class OntoProduct(
    val id: Long,
    val name: String,
    val price: Float,
    val image: String,
    val info: String,
    val proteins: String,
    val fat: String,
    val carbon: String,
    val kcal: String,
    val description: String,
    val inStock: Int
)

data class OntoProductWithSimilar(
    val product: OntoProduct,
    val similarProducts: List<OntoProduct>
)