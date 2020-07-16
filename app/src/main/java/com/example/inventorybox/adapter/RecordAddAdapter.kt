package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.ViewHolder.RecordAddVH


class RecordAddAdapter(private val context: Context) : RecyclerView.Adapter<RecordAddVH>() {
    var datas = mutableListOf<RecordAddData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAddVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_record, parent, false)
        return RecordAddVH(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecordAddVH, position: Int) {
        holder.bind(datas[position])

    }

}
