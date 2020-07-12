package com.example.inventorybox.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.activity.ExchangeItemDetail
import com.example.inventorybox.activity.ExchangePostActivity
import com.example.inventorybox.activity.RecordRecordActivity
import com.example.inventorybox.data.ExchangeData
import com.example.inventorybox.fragment.ExchangeAllFragment
import com.example.inventorybox.fragment.ExchangeProductFragment
import kotlinx.android.synthetic.main.item_exchange.view.*

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

        // item 눌리면 clicklistener
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ExchangeItemDetail::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            it.context.startActivity(intent)
        }
    }
}

class ExchangeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.iv_exchange_item)
    val price = itemView.findViewById<TextView>(R.id.exchange_price)
    val distance = itemView.findViewById<TextView>(R.id.exchange_distance)
    val name = itemView.findViewById<TextView>(R.id.exchange_name)
    val date = itemView.findViewById<TextView>(R.id.exchange_date)
    val time = itemView.findViewById<TextView>(R.id.exchange_time)
    val img_heart = itemView.btn_exchange_like
    var isLiked = false

    fun bind(exchangeData: ExchangeData){
        Glide.with(itemView.context).load(exchangeData.img_url).error(R.drawable.exchangemain_btn_heart).into(img)
        img.clipToOutline=true
        price.text = exchangeData.price
        distance.text = exchangeData.distance
        name.text = exchangeData.name
        date.text = exchangeData.date
        time.text = exchangeData.time

        // img_heart 가 눌리면 채워지도록
        img_heart.setOnClickListener {
            if(isLiked){
                img_heart.setImageResource(R.drawable.exchangemain_btn_heart_unchecked)
                isLiked=false
            }else{
                img_heart.setImageResource(R.drawable.exchangemain_btn_heart_checked)
                isLiked=true
            }
        }
    }
}