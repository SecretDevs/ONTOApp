package com.example.onto.cart.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cart_error.view.*

class CartErrorViewHolder(
    view: View,
    onClick: () -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.cart_error_btn.setOnClickListener { onClick() }
    }

}