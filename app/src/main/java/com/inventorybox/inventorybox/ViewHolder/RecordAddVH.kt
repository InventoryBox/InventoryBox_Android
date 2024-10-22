package com.inventorybox.inventorybox.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.data.RecordHomeItemInfo

class RecordAddVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val input_count = itemView.findViewById<TextView>(R.id.tv_rv_input_stock)


    fun bind(recordAddData: RecordHomeItemInfo){
        Glide.with(itemView).load(recordAddData.img).into(img)
        name.text = recordAddData.name
        //input_count.text = recordAddData.input_count
    }
}