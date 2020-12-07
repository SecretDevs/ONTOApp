package com.example.onto.materials.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MaterialsItemDecoration(
    private val margin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        outRect.set(
            if (position % 3 == 2) margin / 2 else margin,
            margin / 2,
            if (position % 3 == 1) margin / 2 else margin,
            margin / 2
        )
    }
}