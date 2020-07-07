package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.item_graph_detail_calendar.view.*

class GraphDetailWeekCalAdapter(private val context: Context, val max_week:Int): RecyclerView.Adapter<GraphDetailWeekCalHolder>() {

//  	var datas: MutableList<String> = mutableListOf()

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
              if(holder.isClicked){
                  holder.itemView.setBackgroundResource(R.drawable.graph_rec9_white)
                  holder.isClicked=false
              }else{
                  holder.itemView.setBackgroundResource(R.drawable.graph_rec9_yellow)
                  holder.isClicked=true
              }

          }
      }
  }
class GraphDetailWeekCalHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    var isClicked = false
    val tv_week = itemView.tv_week
    fun bind(data : String){
        tv_week.text = data
    }
 }