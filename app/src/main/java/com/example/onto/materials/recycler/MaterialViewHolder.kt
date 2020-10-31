package com.example.onto.materials.recycler

import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
import com.example.onto.R
import com.example.onto.base.recycler.DataViewHolder
import com.example.onto.vo.OntoArticle
import java.lang.IllegalArgumentException

class MaterialViewHolder(
    view: View,
    private val onClick: (Long) -> Unit,

) : DataViewHolder<OntoArticle>(view) {
    private val name = view.findViewById<TextView>(R.id.item_material_name)
    private val date = view.findViewById<TextView>(R.id.item_material_date)
    private lateinit var data: OntoArticle

    init {
        view.setOnClickListener { onClick(data.id) }
    }

    override fun bindData(data: OntoArticle) {
        this.data = data

        name.text = data.name
        try {
            date.text = data.date.substring(0,10)
        }
        catch (e :IndexOutOfBoundsException){
            date.isInvisible
        }
    }


    fun clear() {
         }
}