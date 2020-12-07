package com.example.onto.products.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.recycler.DataViewHolder
import com.example.onto.vo.inapp.OntoProduct
import com.google.android.material.imageview.ShapeableImageView
import java.text.DecimalFormat

class ProductViewHolder(
    view: View,
    private val onClick: (Long) -> Unit,
    private val onAddToCartClick: (Long) -> Unit
) : DataViewHolder<OntoProduct>(view) {
    private val image = view.findViewById<ImageView>(R.id.item_product_image)
    private val name = view.findViewById<TextView>(R.id.item_product_name)
    private val price = view.findViewById<TextView>(R.id.item_product_price)
    private val addToCart = view.findViewById<ShapeableImageView>(R.id.item_product_cart_btn)

    private lateinit var data: OntoProduct

    init {
        view.setOnClickListener { onClick(data.id) }
        addToCart.setOnClickListener { onAddToCartClick(data.id) }
    }

    override fun bindData(data: OntoProduct) {
        this.data = data
        name.text = data.name
        price.text =
            price.context.getString(R.string.price_placeholder, priceFormat.format(data.price))
        Glide.with(image)
            .load(data.image)
            .into(image)
    }

    fun clear() {
        Glide.with(image).clear(image)
    }

    companion object {
        private val priceFormat = DecimalFormat("#.##")
    }

}