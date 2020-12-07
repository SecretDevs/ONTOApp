package com.example.onto.discount.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.recycler.DataViewHolder
import com.example.onto.vo.remote.OntoOffer
import kotlinx.android.synthetic.main.item_discount.view.*
import java.text.DecimalFormat

class DiscountViewHolder(
    view: View,
    private val onClick: (Long) -> Unit,
    private val onAddCLick: (Long) -> Unit,
) : DataViewHolder<OntoOffer>(view) {
    private val image = view.findViewById<ImageView>(R.id.item_product_image)
    private val name = view.findViewById<TextView>(R.id.item_product_name)
    private val price = view.item_product_price
    private val addToCart = view.item_product_cart_btn
    private val badgePercent = view.discount_percent

    private lateinit var data: OntoOffer

    init {
        view.setOnClickListener { onClick(data.id) }
    }

    override fun bindData(data: OntoOffer) {
        this.data = data

        name.text = data.name
        price.text =
            price.context.getString(R.string.price_placeholder, priceFormat.format(data.price))
        Glide.with(image)
            .load(data.image)
            .into(image)
        badgePercent.text = "43%"
    }

    companion object {
        private val priceFormat = DecimalFormat("#.##")
    }

    fun clear() {
        Glide.with(image).clear(image)
    }
}