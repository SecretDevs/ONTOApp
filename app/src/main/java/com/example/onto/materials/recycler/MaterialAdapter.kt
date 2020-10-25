package com.example.onto.materials.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onto.R
import com.example.onto.base.recycler.BaseStateAdapter
import com.example.onto.vo.OntoArticle

class MaterialAdapter(
    onRetry: () -> Unit,
    private val onCLick: (Long) -> Unit,

) : BaseStateAdapter<OntoArticle, MaterialViewHolder>(onRetry) {

    override fun getDataViewHolder(inflater: LayoutInflater, parent: ViewGroup): MaterialViewHolder =
        MaterialViewHolder(
            inflater.inflate(R.layout.item_material, parent, false),
            onCLick,
        )

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is MaterialViewHolder) {
            holder.clear()
        }
    }
}
