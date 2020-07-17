package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.ViewHolder.RecordRecordVH
import com.example.inventorybox.data.RecordRecordItemInfo

class RecordRecordAdapter(private val context: Context) : RecyclerView.Adapter<RecordRecordVH>() {
    var datas = mutableListOf<RecordRecordItemInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordRecordVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_record, parent, false)
        return RecordRecordVH(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecordRecordVH, position: Int) {
        holder.bind(datas[position])

    }

}