package com.example.inventorybox.ViewHolder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.data.RecordAddIconInfo
import com.example.inventorybox.data.RecordIconData

class RecordIconVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.img_icon)

    fun bind(recordIconData: RecordAddIconInfo){
        Glide.with(itemView).load(recordIconData.img).into(img)
    }
}