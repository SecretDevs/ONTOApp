package com.example.onto

import com.example.onto.base.MviIntent

sealed class ExampleIntent : MviIntent {
    object AddOneFirstIntent : ExampleIntent()
    object AddOneSecondIntent : ExampleIntent()
    object AddFiveFirstIntent : ExampleIntent()
    object AddFiveSecondIntent : ExampleIntent()
    object ClearCountersIntent : ExampleIntent()
}