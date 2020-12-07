package com.example.onto.materials.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.onto.R
import com.example.onto.base.recycler.BaseStateAdapter
import com.example.onto.vo.remote.OntoArticle

class MaterialAdapter(
    onRetry: () -> Unit,
    private val onCLick: (Long) -> Unit,
) : BaseStateAdapter<OntoArticle, MaterialViewHolder>(onRetry) {

    override fun getDataViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): MaterialViewHolder =
        MaterialViewHolder(
            inflater.inflate(R.layout.item_material, parent, false),
            onCLick,
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position % 3 == 0 && holder.itemView.layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            (holder.itemView.layoutParams
                    as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
        }
        if (holder is MaterialViewHolder) {
            holder.bindData(dataList[position], position % 3 == 0)
        } else {
            if (holder.itemView.layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                (holder.itemView.layoutParams
                        as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
            }
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is MaterialViewHolder) {
            holder.clear()
        }
    }
}
