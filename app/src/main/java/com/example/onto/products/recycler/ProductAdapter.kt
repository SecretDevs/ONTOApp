package com.example.onto.products.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onto.R
import com.example.onto.base.recycler.BaseStateAdapter
import com.example.onto.vo.OntoProduct

class ProductAdapter(
    onRetry: () -> Unit,
    private val onCLick: (Long) -> Unit,
    private val onAddToCartClick: (Long) -> Unit
) : BaseStateAdapter<OntoProduct, ProductViewHolder>(onRetry) {

    override fun getDataViewHolder(inflater: LayoutInflater, parent: ViewGroup): ProductViewHolder =
        ProductViewHolder(
            inflater.inflate(R.layout.item_product, parent, false),
            onCLick,
            onAddToCartClick
        )

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is ProductViewHolder) {
            holder.clear()
        }
    }
}