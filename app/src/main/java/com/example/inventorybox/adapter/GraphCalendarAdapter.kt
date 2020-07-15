package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R

class GraphCalendarAdapter(private val context: Context): RecyclerView.Adapter<GraphCalendarViewHolder>() {

  	var datas: MutableList<String> = mutableListOf()
    //오늘 요일을 int로
    var today :Int=4

  	// xml file을 inflate한 후 viewHolder를 만든다.
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraphCalendarViewHolder {
// fortest
//        datas = mutableListOf(3,4,5,6,7,8,9)
  	    val view = LayoutInflater.from(context).inflate(R.layout.item_graph_calendar, parent,false)
  		return GraphCalendarViewHolder(view)
      }

      override fun getItemCount(): Int {
  		 return 7
      }
  	  // viewholder의 항목을 구성하기 위해 호출된다.
      override fun onBindViewHolder(holder: GraphCalendarViewHolder, position: Int) {
          if(datas.isNotEmpty()){
              holder.bind(datas[position], position)
              if (position==today){
                  holder.selectToday()
              }

          }
      }

  }
class GraphCalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val day : TextView = itemView.findViewById(R.id.graph_cal_day)
    val date : TextView = itemView.findViewById(R.id.graph_cal_date)
    val DAYS = arrayListOf<String>("일","월","화","수","목","금","토")

    //오늘 날짜일 때 호출되어 selected되도
    fun selectToday(){
        itemView.setBackgroundResource(R.drawable.graph_rec13_yellow)
        day.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
        date.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
    }

    fun bind(data : String, index : Int){
        if(index<7)
            day.text=DAYS.get(index)
        date.text=data
    }
}
