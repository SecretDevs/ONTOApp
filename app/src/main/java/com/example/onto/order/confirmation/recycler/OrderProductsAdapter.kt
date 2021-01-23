package com.example.onto.order.confirmation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onto.R
import com.example.onto.cart.recycler.CartItemViewHolder
import com.example.onto.vo.inapp.CartItem

class OrderProductsAdapter : RecyclerView.Adapter<CartItemViewHolder>() {
    private val items = mutableListOf<CartItem>()

    fun updateData(newData: List<CartItem>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder =
        CartItemViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_cart_product,
                parent,
                false
            ),
            onClick = {},
            onRemoveClick = {},
            onAddClick = {},
            onMinusClick = {}
        )

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int = items.size

}