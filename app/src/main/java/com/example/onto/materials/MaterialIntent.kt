package com.example.onto.materials

import com.example.onto.base.MviIntent
import com.example.onto.products.ProductsIntent

sealed class MaterialIntent : MviIntent {

    object InitialIntent : MaterialIntent()

    object ReloadIntent : MaterialIntent()

    object RefreshIntent : MaterialIntent()
}