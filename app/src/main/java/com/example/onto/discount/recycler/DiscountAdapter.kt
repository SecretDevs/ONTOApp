package com.example.onto.discount.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onto.R
import com.example.onto.base.recycler.BaseStateAdapter
import com.example.onto.vo.remote.OntoOffer

class DiscountAdapter(
    onRetry: () -> Unit,
    private val onCLick: (Long) -> Unit,
    private val onAddCLick: (Long) -> Unit
) : BaseStateAdapter<OntoOffer, DiscountViewHolder>(onRetry) {

    override fun getDataViewHolder(inflater: LayoutInflater, parent: ViewGroup): DiscountViewHolder =
        DiscountViewHolder(
            inflater.inflate(R.layout.item_discount, parent, false),
            onCLick,
            onAddCLick
        )

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is DiscountViewHolder) {
            holder.clear()
        }
    }
}
