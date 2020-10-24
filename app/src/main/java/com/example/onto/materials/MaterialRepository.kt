package com.example.onto.materials

import com.example.onto.ExampleActivity
import com.example.onto.vo.OntoArticle

class MaterialRepository() {

    fun updateMaterials(): List<OntoArticle> {
            return downloadMaterials()
    }


    private fun downloadMaterials() : List<OntoArticle>{
        lateinit var list: List<OntoArticle>

        Runnable {
             list =  emptyList()
        }

     return list
    }
}