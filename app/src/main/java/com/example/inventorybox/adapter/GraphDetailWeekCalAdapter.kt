package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.fragment.onMyChangeListener
import com.example.inventorybox.getColorFromRes
import kotlinx.android.synthetic.main.item_graph_detail_calendar.view.*

class GraphDetailWeekCalAdapter(private val context: Context, val max_week:Int): RecyclerView.Adapter<GraphDetailWeekCalHolder>() {

//  	var datas: MutableList<String> = mutableListOf()
    lateinit var listener: onMyChangeListener
  	// xml file을 inflate한 후 viewHolder를 만든다.
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  GraphDetailWeekCalHolder{
  	    val view = LayoutInflater.from(context).inflate(R.layout.item_graph_detail_calendar, parent,false)
  		return GraphDetailWeekCalHolder(view)
      }

      override fun getItemCount(): Int {
  		 return max_week
      }
  	  // viewholder의 항목을 구성하기 위해 호출된다.
      override fun onBindViewHolder(holder: GraphDetailWeekCalHolder, position: Int) {
//          holder.bind(datas[position])
          val week_name = when(position){
              0->"첫째주"
              1->"둘째주"
              2->"셋째주"
              3->"넷째주"
              else->"다섯째주"
          }
          holder.bind(week_name)
          holder.itemView.setOnClickListener {
              //지금 안보이는 상태이면 보이게 만들고 visible 하게 만들기
              if(holder.isClicked){
//                  holder.itemView.setBackgroundResource(R.drawable.graph_rec9_white)
//                  holder.itemView.tv_week.setTextColor(holder.itemView.context.getColorFromRes(R.color.darkgrey))
                  holder.backgroundToGrey()
//                  holder.isClicked=false
                  listener.onChange(position, false)
              }else{
//                  holder.itemView.setBackgroundResource(R.drawable.graph_rec9_yellow)
//                  holder.itemView.tv_week.setTextColor(holder.itemView.context.getColorFromRes(R.color.white))
                  holder.backgroundToYellow()
//                  holder.isClicked=true
                  listener.onChange(position, true)
              }

          }
      }
    fun set(listener : onMyChangeListener){
        this.listener=listener
    }
  }
class GraphDetailWeekCalHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    var isClicked = false
    val tv_week = itemView.tv_week
    fun bind(data : String){
        tv_week.text = data
        //처음에는 background yellow로
    }
    //background 노란색으로 바꾸는
    fun backgroundToYellow(){
        itemView.setBackgroundResource(R.drawable.graph_rec9_yellow)
        tv_week.setTextColor(itemView.context.getColorFromRes(R.color.white))
        isClicked=true
    }
    fun backgroundToGrey(){
        itemView.setBackgroundResource(R.drawable.graph_rec9_white)
        tv_week.setTextColor(itemView.context.getColorFromRes(R.color.darkgrey))
        isClicked=false
    }
 }