package com.example.onto.cart.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onto.R
import com.example.onto.base.recycler.BaseStateAdapter
import com.example.onto.vo.inapp.CartItem
import com.example.onto.vo.local.LocalCartItemWithProduct

class CartAdapter(
    private val onEmptyClick: () -> Unit,
    private val onErrorClick: () -> Unit,
    private val onProductClick: (Long) -> Unit,
    private val onRemoveClick: (Long) -> Unit,
    private val onMinusClick: (Long) -> Unit,
    private val onAddClick: (Long) -> Unit
) : BaseStateAdapter<CartItem, CartItemViewHolder>(onErrorClick) {

    override fun getEmptyViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder = CartEmptyViewHolder(
        inflater.inflate(R.layout.item_cart_empty, parent, false),
        onEmptyClick
    )

    override fun getErrorViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder = CartErrorViewHolder(
        inflater.inflate(R.layout.item_cart_error, parent, false),
        onErrorClick
    )

    override fun getDataViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): CartItemViewHolder = CartItemViewHolder(
        view = inflater.inflate(R.layout.item_cart_product, parent, false),
        onClick = onProductClick,
        onAddClick = onAddClick,
        onMinusClick = onMinusClick,
        onRemoveClick = onRemoveClick
    )

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is CartItemViewHolder) {
            holder.clear()
        }
    }
}