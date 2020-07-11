package com.example.inventorybox.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.data.RecordModifyData

class RecordModifyVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val input_count = itemView.findViewById<TextView>(R.id.tv_rv_input_stock)


    fun bind(recordModifyData: RecordModifyData){
        Glide.with(itemView).load(recordModifyData.img).into(img)
        name.text = recordModifyData.name
        input_count.text = recordModifyData.input_count.toString()
    }
}