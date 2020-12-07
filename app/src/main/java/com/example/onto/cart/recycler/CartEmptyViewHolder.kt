package com.example.onto.cart.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cart_empty.view.*

class CartEmptyViewHolder(
    view: View,
    onClick: () -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.cart_empty_btn.setOnClickListener { onClick() }
    }

}