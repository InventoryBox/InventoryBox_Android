package com.example.inventorybox.etc

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HomeOrderRecyclerViewDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        outRect.bottom=30
    }
}