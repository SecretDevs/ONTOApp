package com.example.onto.materials

import com.example.onto.ExampleEffect
import com.example.onto.base.MviEffect
import com.example.onto.vo.OntoArticle

sealed class MaterialEffect : MviEffect {
    class UpdateMaterials(
        val materials: List<OntoArticle>
    ) : MaterialEffect()
}