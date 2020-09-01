package com.example.inventorybox.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.activity.ExchangeItemDetail
import com.example.inventorybox.data.PostInfo
import com.example.inventorybox.data.PostItemInfo
import com.example.inventorybox.graph.getColorFromRes
import kotlinx.android.synthetic.main.item_exchange_mypost.view.*
import java.text.DecimalFormat

class ExchangeMyPostAdapter(private val context: Context, val isSold :Boolean): RecyclerView.Adapter<ExchangeMyPostHolder>() {

  	var datas: MutableList<PostInfo> = mutableListOf()

  	// xml file을 inflate한 후 viewHolder를 만든다.
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeMyPostHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_exchange_mypost, parent,false)

  		return ExchangeMyPostHolder(view)
      }

      override fun getItemCount(): Int {
  		 return datas.size
      }
  	  // viewholder의 항목을 구성하기 위해 호출된다.
      override fun onBindViewHolder(holder: ExchangeMyPostHolder, position: Int) {
          holder.bind(datas[position], isSold)
          holder.itemView.setOnClickListener {
              val intent = Intent(it.context, ExchangeItemDetail::class.java)
              intent.putExtra("post_idx", datas[position].postIdx)
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
              it.context.startActivity(intent)
          }
      }
  }
class ExchangeMyPostHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(data : PostInfo, isSold: Boolean){
        itemView.exchange_mypost_img.clipToOutline=true
        if(isSold){
            itemView.exchange_mypost_bg.setBackgroundColor(getColorFromRes(itemView.context, R.color.lightgrey))
            itemView.tv_exchange_mypost_sold.visibility = View.VISIBLE
        }else{
            itemView.exchange_mypost_bg.setBackgroundColor(getColorFromRes(itemView.context, R.color.default_background))
            itemView.tv_exchange_mypost_sold.visibility = View.GONE
        }
        itemView.item_mypost_product.text = data.productName
        if(data.expDate.isNullOrEmpty()){
            itemView.item_mypost_expire_date.text = "유통기한 없음"
        }else{
            itemView.item_mypost_expire_date.text = "유통기한 "+data.expDate
        }
        itemView.item_mypost_price.text = currencyFormat(data.price)
        Glide.with(itemView.context).load(data.productImg).error(R.drawable.exchangemain_btn_heart).into(itemView.exchange_mypost_img)
        itemView.item_mypost_datetime.text = data.uploadDate

    }
    fun currencyFormat(amount : Int): String{
        val formatter = DecimalFormat("###,###,###")
        return formatter.format(amount)+"원"
    }
 }