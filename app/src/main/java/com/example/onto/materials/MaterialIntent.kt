package com.example.onto.materials

import com.example.onto.base.MviIntent

sealed class MaterialIntent : MviIntent {

    object InitialIntent : MaterialIntent()

    object ReloadIntent : MaterialIntent()

    object RefreshIntent : MaterialIntent()

    data class NavigateToMaterialDetailsIntent(
        val materialId: Long
    ) : MaterialIntent()

    object NavigateToCartIntent : MaterialIntent()

    object UpdateCartIntent : MaterialIntent()
}