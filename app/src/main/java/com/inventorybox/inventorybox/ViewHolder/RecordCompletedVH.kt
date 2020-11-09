package com.inventorybox.inventorybox.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.data.RecordHomeItemInfo

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
        if(recordCompletedData.stocksCnt<0){
            count_stock.text = "미입력"
            count_stock.setTextColor(itemView.context.getColor(R.color.middlegrey))
            count_stock.textSize = 13f
        }else{
            count_stock.text = recordCompletedData.stocksCnt.toString()
            count_stock.setTextColor(itemView.context.getColor(R.color.grey))
            count_stock.textSize = 15f
        }
    }
}