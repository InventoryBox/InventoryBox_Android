package com.example.inventorybox.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
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
import com.example.inventorybox.data.PostInfo
import com.example.inventorybox.fragment.ExchangeAllFragment
import com.example.inventorybox.fragment.ExchangeProductFragment
import kotlinx.android.synthetic.main.item_exchange.view.*
import java.text.DecimalFormat

class ExchangeRVAdapter (private val context: Context):RecyclerView.Adapter<ExchangeViewHolder>(){
    var datas:MutableList<PostInfo> = mutableListOf()
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
            intent.putExtra("post_idx", datas[position].postIdx)
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
    val date = itemView.findViewById<TextView>(R.id.tv_post_date)
    val expire_date = itemView.findViewById<TextView>(R.id.tv_expire_date)
    val img_heart = itemView.btn_exchange_like
    var isLiked = false

    fun bind(data: PostInfo){
        Glide.with(itemView.context).load(data.productImg).error(R.drawable.exchangemain_btn_heart).into(img)
        img.clipToOutline=true
//        price.text = data.price.toString()+"원"
        price.text = currencyFormat(data.price)
        // distDiff 가 1000이하이면 글씨색 grey else yellow
        if(data.distDiff<=1000){
            distance.setTextColor(itemView.context.getColor(R.color.yellow))
        }else{
            distance.setTextColor(itemView.context.getColor(R.color.grey))
        }
        distance.text = computeDistance(data.distDiff)
        name.text = data.productName
        date.text = data.uploadDate
        expire_date.text = if(data.expDate.isNullOrBlank()) "유통기한 없음" else "유통기한 "+data.expDate
//        Log.d("exchangervadapter","${data.expDate}")
//        time.text = data.expDate.toString()

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
    fun computeDistance(dist : Int) : String{
        if(dist<1000){
            return dist.toString()+"m"
        }else{
            return "%.1fkm".format(dist.toDouble()/1000)
        }
    }

    fun currencyFormat(amount : Int): String{
        val formatter = DecimalFormat("###,###,###")
        return formatter.format(amount)+"원"
    }
}