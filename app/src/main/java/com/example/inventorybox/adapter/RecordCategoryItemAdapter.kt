package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.ViewHolder.RecordAddVH
import com.example.inventorybox.ViewHolder.RecordCategoryITemVH
import com.example.inventorybox.data.RecordAddData
import com.example.inventorybox.data.RecordHomeItemInfo

class RecordCategoryItemAdapter(private val context: Context) : RecyclerView.Adapter<RecordCategoryITemVH>() {
    var datas = mutableListOf<RecordHomeItemInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordCategoryITemVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_edit, parent, false)
        return RecordCategoryITemVH(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecordCategoryITemVH, position: Int) {
        holder.bind(datas[position])

    }

}