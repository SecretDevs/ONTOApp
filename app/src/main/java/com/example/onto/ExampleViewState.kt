package com.example.onto

import com.example.onto.base.MviViewState

data class ExampleViewState(
    val firstCounter: Int = 0,
    val secondCounter: Int = 0
) : MviViewState