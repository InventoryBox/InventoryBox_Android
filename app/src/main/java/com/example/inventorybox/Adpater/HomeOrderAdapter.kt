package com.example.inventorybox.Adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.Data.HomeOrderData
import com.example.inventorybox.HomeOrderViewHoler
import com.example.inventorybox.R

class HomeOrderAdapter(private val context: Context) : RecyclerView.Adapter<HomeOrderViewHoler>() {
    var datas = mutableListOf<HomeOrderData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOrderViewHoler {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_orderlist, parent, false)
        return HomeOrderViewHoler(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomeOrderViewHoler, position: Int) {
        holder.bind(datas[position])

    }




}