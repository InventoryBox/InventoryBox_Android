package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.Data.HomeTodayOrderData
import com.example.inventorybox.HomeTodayOrderViewHolder
import com.example.inventorybox.R

class HomeTodayOrderAdapter(private val context: Context) : RecyclerView.Adapter<HomeTodayOrderViewHolder>() {
    var datas = mutableListOf<HomeTodayOrderData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTodayOrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_today_order, parent, false)
        return HomeTodayOrderViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomeTodayOrderViewHolder, position: Int) {
        holder.bind(datas[position])
    }


}
