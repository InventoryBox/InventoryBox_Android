package com.inventorybox.inventorybox.ViewHolder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.data.RecordAddIconInfo

class RecordIconVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.img_icon)

    fun bind(recordIconData: RecordAddIconInfo){
        Glide.with(itemView).load(recordIconData.img).into(img)
    }
}