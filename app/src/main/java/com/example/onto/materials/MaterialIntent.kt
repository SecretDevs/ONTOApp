package com.example.onto.materials

import com.example.onto.base.MviIntent

sealed class MaterialIntent : MviIntent {
    object InitialIntent : MaterialIntent()
    object RefreshIntent : MaterialIntent()
}