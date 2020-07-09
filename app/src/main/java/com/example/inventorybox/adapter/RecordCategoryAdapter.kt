package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.ViewHolder.RecordCategoryVH
import com.example.inventorybox.data.RecordCategoryData

class RecordCategoryAdapter(private val context: Context) : RecyclerView.Adapter<RecordCategoryVH>() {
    var datas = mutableListOf<RecordCategoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordCategoryVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_edit, parent, false)
        return RecordCategoryVH(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecordCategoryVH, position: Int) {
        holder.bind(datas[position])

    }

}