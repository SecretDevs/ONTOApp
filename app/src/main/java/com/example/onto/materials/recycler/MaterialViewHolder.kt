package com.example.onto.materials.recycler

import android.view.View
import android.widget.TextView
import com.example.onto.R
import com.example.onto.base.recycler.DataViewHolder
import com.example.onto.vo.OntoArticle

class MaterialViewHolder(
    view: View,
    private val onClick: (Long) -> Unit,

) : DataViewHolder<OntoArticle>(view) {
    private val name = view.findViewById<TextView>(R.id.item_material_description)
    private lateinit var data: OntoArticle

    init {
        view.setOnClickListener { onClick(data.id) }
    }

    override fun bindData(data: OntoArticle) {
        this.data = data
        name.text = data.name
    }


    fun clear() {
         }
}