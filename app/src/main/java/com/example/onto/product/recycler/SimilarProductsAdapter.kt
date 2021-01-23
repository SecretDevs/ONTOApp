package com.example.onto.product.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onto.R
import com.example.onto.products.recycler.ProductViewHolder
import com.example.onto.vo.inapp.OntoProduct
import timber.log.Timber

class SimilarProductsAdapter(
    private val onCLick: (Long) -> Unit,
    private val onAddToCartClick: (Long) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val data = mutableListOf<OntoProduct>()

    fun updateData(newData: List<OntoProduct>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_similar_product,
                parent,
                false
            ),
            onCLick,
            onAddToCartClick
        )

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        Timber.d(data.size.toString())
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int = data.size

}