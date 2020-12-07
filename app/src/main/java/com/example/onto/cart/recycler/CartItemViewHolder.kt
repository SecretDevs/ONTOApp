package com.example.onto.cart.recycler

import android.view.View
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.recycler.DataViewHolder
import com.example.onto.vo.inapp.CartItem
import kotlinx.android.synthetic.main.item_cart_product.view.*
import java.text.DecimalFormat

class CartItemViewHolder(
    private val view: View,
    onClick: (Long) -> Unit,
    onRemoveClick: (Long) -> Unit,
    onAddClick: (Long) -> Unit,
    onMinusClick: (Long) -> Unit
) : DataViewHolder<CartItem>(view) {
    private lateinit var item: CartItem

    init {
        view.setOnClickListener { onClick(item.product.id) }
        view.cart_product_remove.setOnClickListener { onRemoveClick(item.product.id) }
        view.cart_product_minus_btn.setOnClickListener { onMinusClick(item.product.id) }
        view.cart_product_plus_btn.setOnClickListener { onAddClick(item.product.id) }
    }

    override fun bindData(data: CartItem) {
        item = data
        Glide.with(view.cart_product_image)
            .load(data.product.image)
            .into(view.cart_product_image)
        view.cart_product_title.text = data.product.name
        view.cart_product_quantity.text = data.quantity.toString()
        view.cart_product_minus_btn.isEnabled = data.quantity != 1
        view.cart_product_price.text =
            view.resources.getString(
                R.string.price_placeholder,
                priceFormat.format(data.product.price)
            )
    }

    fun clear() {
        Glide.with(view.cart_product_image).clear(view.cart_product_image)
    }

    companion object {
        private val priceFormat = DecimalFormat("#.##")
    }

}