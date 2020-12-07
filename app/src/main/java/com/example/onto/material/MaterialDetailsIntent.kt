package com.example.onto.material

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class MaterialDetailsIntent : MviIntent {

    data class InitialIntent(
        val articleId: Long
    ) : MaterialDetailsIntent()

    data class ReloadIntent(
        val articleId: Long
    ) : MaterialDetailsIntent()

    object GoBackIntent : MaterialDetailsIntent()

    object MaterialDetailsNothingIntent : MaterialDetailsIntent(), NothingIntent

}