package com.example.inventorybox.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.data.RecordHomeItemInfo

class RecordCompletedVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val unit = itemView.findViewById<TextView>(R.id.tv_rv_unit)
    val count_noti = itemView.findViewById<TextView>(R.id.tv_rv_count_noti)
    val count_stock = itemView.findViewById<TextView>(R.id.tv_rv_count_stock)



    fun bind(recordCompletedData: RecordHomeItemInfo){
        Glide.with(itemView).load(recordCompletedData.img).into(img)
        name.text = recordCompletedData.name
        unit.text = recordCompletedData.unit
        count_noti.text = recordCompletedData.alarmCnt.toString()
        count_stock.text = recordCompletedData.stocksCnt.toString()
    }
}