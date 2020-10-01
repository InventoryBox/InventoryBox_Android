package com.inventorybox.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.ViewHolder.RecordModifyVH
import com.inventorybox.inventorybox.data.RecordModifyItemInfo


class RecordModifyAdapter(private val context: Context) : RecyclerView.Adapter<RecordModifyVH>() {
    var datas = mutableListOf<RecordModifyItemInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordModifyVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_modify, parent, false)
        return RecordModifyVH(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecordModifyVH, position: Int) {
        holder.bind(datas[position])

    }

}