package com.example.onto.products.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ProductItemDecoration(
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
            if (position % 2 == 0) margin else margin / 2,
            margin / 2,
            if (position % 2 == 1) margin else margin / 2,
            margin / 2
        )
    }
}
