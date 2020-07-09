package com.example.inventorybox.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.data.RecordAddData

class RecordAddVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val input_count = itemView.findViewById<TextView>(R.id.tv_rv_input_stock)


    fun bind(recordCompletedData: RecordAddData){
        Glide.with(itemView).load(recordCompletedData.img).into(img)
        name.text = recordCompletedData.name
        input_count.text = recordCompletedData.input_count.toString()
    }
}