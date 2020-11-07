package com.example.onto.materials.recycler

import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.recycler.DataViewHolder
import com.example.onto.vo.OntoArticle
import com.google.android.material.imageview.ShapeableImageView
import java.lang.IllegalArgumentException

class MaterialViewHolder(
    view: View,
    private val onClick: (Long) -> Unit,

) : DataViewHolder<OntoArticle>(view) {
    private val name = view.findViewById<TextView>(R.id.item_material_name)
    private val image = view.findViewById<ShapeableImageView>(R.id.item_material_image)

    private lateinit var data: OntoArticle

    init {
        view.setOnClickListener { onClick(data.id) }
    }

    override fun bindData(data: OntoArticle) {
        this.data = data

        name.text = data.name

        Glide.with(image)
            .load(data.image)
            .into(image)

    }


    fun clear() {
        Glide.with(image).clear(image)
    }
}