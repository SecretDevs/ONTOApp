package com.example.onto.vo.inapp

data class OntoProduct(
    val id: Long,
    val name: String,
    val price: Float,
    val image: String,
    val info: String,
    val description: String,
    val isInStock: Boolean
)