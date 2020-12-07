package com.example.onto.materials.recycler

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.recycler.DataViewHolder
import com.example.onto.vo.remote.OntoArticle
import kotlinx.android.synthetic.main.item_material.view.*

class MaterialViewHolder(
    private val view: View,
    private val onClick: (Long) -> Unit,
) : DataViewHolder<OntoArticle>(view) {

    private lateinit var data: OntoArticle

    init {
        view.setOnClickListener { onClick(data.id) }
    }

    override fun bindData(data: OntoArticle) {
        throw UnsupportedOperationException("Wrong realization!")
    }

    fun bindData(data: OntoArticle, isFullSpan: Boolean) {
        (view.item_material_image.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio =
            if (isFullSpan) "2:1" else "1:1"
        this.data = data

        view.item_material_name.text = data.name

        Glide.with(view.item_material_image)
            .load(data.image)
            .into(view.item_material_image)
        view.elevation = view.resources.getDimension(R.dimen.gutter_default)
    }


    fun clear() {
        Glide.with(view.item_material_image).clear(view.item_material_image)
    }
}