package com.example.onto.utils

import java.text.DecimalFormat

private val priceFormatter = DecimalFormat("#.##")

fun formatPrice(price: Float): String = priceFormatter.format(price)