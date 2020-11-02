package com.example.onto.material

import com.example.onto.base.MviIntent

sealed class MaterialDetailsIntent : MviIntent {

    data class InitialIntent(
        val articleId: Long
    ) : MaterialDetailsIntent()

    data class ReloadIntent(
        val articleId: Long
    ) : MaterialDetailsIntent()

}