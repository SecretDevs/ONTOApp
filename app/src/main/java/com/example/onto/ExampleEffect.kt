package com.example.onto

import com.example.onto.base.MviEffect

sealed class ExampleEffect : MviEffect {
    class UpdateCounter(
        val valueFirst: Int,
        val valueSecond: Int
    ) : ExampleEffect()
}