package com.example.onto.discount.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onto.R
import com.example.onto.base.recycler.BaseStateAdapter
import com.example.onto.vo.OntoArticle

class DiscountAdapter(
    onRetry: () -> Unit,
    private val onCLick: (Long) -> Unit,

) : BaseStateAdapter<OntoArticle, DiscountlViewHolder>(onRetry) {

    override fun getDataViewHolder(inflater: LayoutInflater, parent: ViewGroup): DiscountlViewHolder =
        DiscountlViewHolder(
            inflater.inflate(R.layout.item_material, parent, false),
            onCLick,
        )

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is DiscountlViewHolder) {
            holder.clear()
        }
    }
}
