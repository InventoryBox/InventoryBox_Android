package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.data.ExchangeData

class ExchangeRVAdapter (private val context: Context):RecyclerView.Adapter<ExchangeViewHolder>(){
    var datas:MutableList<ExchangeData> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_exchange, parent, false)
        return ExchangeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}

class ExchangeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.iv_exchange_item)
    val price = itemView.findViewById<TextView>(R.id.exchange_price)
    val distance = itemView.findViewById<TextView>(R.id.exchange_distance)
    val name = itemView.findViewById<TextView>(R.id.exchange_name)
    val date = itemView.findViewById<TextView>(R.id.exchange_date)
    val time = itemView.findViewById<TextView>(R.id.exchange_time)

    fun bind(exchangeData: ExchangeData){
        Glide.with(itemView).load(exchangeData.img_url).into(img)
        price.text = exchangeData.price
        distance.text = exchangeData.distance
        name.text = exchangeData.name
        date.text = exchangeData.date
        time.text = exchangeData.time
    }
}