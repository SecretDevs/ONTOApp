package com.example.onto.discount

import com.example.onto.base.MviIntent

sealed class DiscountIntent : MviIntent {

    object InitialIntent : DiscountIntent()

    object ReloadIntent : DiscountIntent()

    object RefreshIntent : DiscountIntent()
}