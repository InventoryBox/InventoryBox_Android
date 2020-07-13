package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.ViewHolder.RecordAddVH
import com.example.inventorybox.ViewHolder.RecordIconVH
import com.example.inventorybox.data.RecordAddData
import com.example.inventorybox.data.RecordIconData

class RecordIconAdapter(private val context: Context) : RecyclerView.Adapter<RecordIconVH>() {
    var datas = mutableListOf<RecordIconData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordIconVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_icon, parent, false)
        return RecordIconVH(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecordIconVH, position: Int) {
        holder.bind(datas[position])

    }

}