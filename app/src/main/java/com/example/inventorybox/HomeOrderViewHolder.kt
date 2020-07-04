package com.example.inventorybox

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.Data.HomeOrderData

class HomeOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mData: ArrayList<String>? = null

    val index = itemView.findViewById<ConstraintLayout>(R.id.rv_home_container)
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val count = itemView.findViewById<TextView>(R.id.tv_rv_count)

    val btn_more = itemView.findViewById<ImageButton>(R.id.btn_rv_more)

    // 생성자에서 데이터 리스트 객체를 전달받음.
    /*fun SimpleTextAdapter(list: ArrayList<String?>) {
        mData = list
    }*/




    fun bind(homeOrderData: HomeOrderData){
        Glide.with(itemView).load(homeOrderData.img).into(img)
        name.text = homeOrderData.name
        count.text = homeOrderData.count.toString()
    }
}