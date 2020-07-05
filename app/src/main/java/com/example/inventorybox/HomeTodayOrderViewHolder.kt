package com.example.inventorybox

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.Data.HomeTodayOrderData

class HomeTodayOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val index = itemView.findViewById<ConstraintLayout>(R.id.rv_home_today_container)
    val name = itemView.findViewById<TextView>(R.id.tv_home_today)
    val check = itemView.findViewById<ImageView>(R.id.iv_home_today_check)

    fun bind(homeTodayOrderData: HomeTodayOrderData){
        name.text = homeTodayOrderData.name

    }
}