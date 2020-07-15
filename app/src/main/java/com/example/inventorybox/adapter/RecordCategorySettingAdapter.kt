package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.data.RecordCategorySettingData
import kotlinx.android.synthetic.main.activity_add.*

class RecordCategorySettingAdapter(private val context: Context) : RecyclerView.Adapter<RecordCategorySettingVH>(){
    var datas = mutableListOf<RecordCategorySettingData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordCategorySettingVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category_bottomsheet, parent, false)
        return RecordCategorySettingVH(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecordCategorySettingVH, position: Int) {
        holder.bind(datas[position])
    }

}

class RecordCategorySettingVH(itemView: View) : RecyclerView.ViewHolder(itemView){
    val name = itemView.findViewById<TextView>(R.id.tv_category)
    fun bind(data: RecordCategorySettingData){
        name.text = data.name
    }

}