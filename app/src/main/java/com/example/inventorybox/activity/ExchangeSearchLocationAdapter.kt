package com.example.inventorybox.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.data.Address
import kotlinx.android.synthetic.main.item_exchange_rv_location.view.*


class ExchangeSearchLocationAdapter(private val context: Context): RecyclerView.Adapter<ExchangeSearchHolder>() {

  	var datas: MutableList<Address> = mutableListOf()
    private var selected_pos = -1
    lateinit var adapter_listener : ExchangeSetLocation.MyItemClickListener

  	// xml file을 inflate한 후 viewHolder를 만든다.
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeSearchHolder {
  	    val view = LayoutInflater.from(context).inflate(R.layout.item_exchange_rv_location, parent,false)
  		return ExchangeSearchHolder(view)
      }

      override fun getItemCount(): Int {
  		 return datas.size
      }
  	  // viewholder의 항목을 구성하기 위해 호출된다.
      override fun onBindViewHolder(holder: ExchangeSearchHolder, position: Int) {
          let{

              holder.bind(datas[position])
          }
          if(position==selected_pos){
              holder.set_selected()
          }else{
              holder.set_unselected()
          }
          holder.itemView.setOnClickListener {
              selected_pos=position
              notifyDataSetChanged()
              adapter_listener.onClick(datas[position])
          }
      }

    fun setListener(listener : ExchangeSetLocation.MyItemClickListener){
        this.adapter_listener = listener
    }
    fun setClear(){
        selected_pos=-1
    }
  }

class ExchangeSearchHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    val tv_loca = itemView.tv_loca
    fun bind(data : Address){
        tv_loca.setText(data.address_name)
    }
    fun set_selected(){
        tv_loca.setTextColor(itemView.context.getColor(R.color.yellow))
    }
    fun set_unselected(){
        tv_loca.setTextColor(itemView.context.getColor(R.color.darkgrey))
    }
 }