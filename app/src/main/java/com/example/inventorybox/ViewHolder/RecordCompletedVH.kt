package com.example.inventorybox.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.Data.RecordCompletedData
import com.example.inventorybox.R

class RecordCompletedVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val count_noti = itemView.findViewById<TextView>(R.id.tv_rv_count_noti)
    val count_stock = itemView.findViewById<TextView>(R.id.tv_rv_count_stock)



    fun bind(recordCompletedData: RecordCompletedData){
        Glide.with(itemView).load(recordCompletedData.img).into(img)
        name.text = recordCompletedData.name
        count_noti.text = recordCompletedData.count_noti.toString()
        count_stock.text = recordCompletedData.count_stock.toString()
    }
}