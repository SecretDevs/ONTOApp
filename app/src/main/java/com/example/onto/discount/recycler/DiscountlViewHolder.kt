package com.example.onto.discount.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.recycler.DataViewHolder
import com.example.onto.products.recycler.ProductViewHolder
import com.example.onto.vo.OntoOffer
import com.google.android.material.imageview.ShapeableImageView
import java.text.DecimalFormat

class DiscountlViewHolder(
    view: View,
    private val onClick: (Long) -> Unit,

) : DataViewHolder<OntoOffer>(view) {
    private val name = view.findViewById<TextView>(R.id.item_discount_name)
    private val price = view.findViewById<TextView>(R.id.item_discount_pice)
    private val description = view.findViewById<TextView>(R.id.item_discount_description)
    private val image = view.findViewById<ShapeableImageView>(R.id.item_dicount_image)

    private lateinit var data: OntoOffer

    init {
        view.setOnClickListener { onClick(data.id) }
    }

    override fun bindData(data: OntoOffer) {
        this.data = data

        name.text = data.name
        price.text = price.context.getString(R.string.price_placeholder, priceFormat.format(data.price))
        description.text = data.offerInfo
        Glide.with(image)
            .load(data.image)
            .into(image)
    }

    companion object {
        private val priceFormat = DecimalFormat("#.##")
    }

    fun clear() {
         }
}