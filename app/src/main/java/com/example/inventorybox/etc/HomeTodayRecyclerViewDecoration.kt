package com.example.inventorybox.etc

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HomeTodayRecyclerViewDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        //아래 여백
        outRect.bottom = 38

        //짝수번째만 여백 주기
        if(position%2 !=0){
            outRect.left = 75
        }
    }
}