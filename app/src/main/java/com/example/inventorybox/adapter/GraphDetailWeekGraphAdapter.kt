package com.example.inventorybox.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.data.GraphSingleWeekData
import com.example.inventorybox.graph.drawSingleGraph
import kotlinx.android.synthetic.main.item_graph_detail_calendar.view.tv_week
import kotlinx.android.synthetic.main.item_graph_detail_graph_weeks.view.*

class GraphDetailWeekGraphAdapter(private val context: Context): RecyclerView.Adapter<GraphDetailWeekGraphHolder>() {

    var datas: MutableList<GraphSingleWeekData> = mutableListOf()
  	// xml file을 inflate한 후 viewHolder를 만든다.
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  GraphDetailWeekGraphHolder{
  	    val view = LayoutInflater.from(context).inflate(R.layout.item_graph_detail_graph_weeks, parent,false)
  		return GraphDetailWeekGraphHolder(view)
      }

      override fun getItemCount(): Int {
  		 return datas.size
      }
  	  // viewholder의 항목을 구성하기 위해 호출된다.
      override fun onBindViewHolder(holder: GraphDetailWeekGraphHolder, position: Int) {
          holder.bind(datas[position], position)

//            holder.itemView.setOnClickListener {
//                holder.itemView.visibility=View.GONE
//                holder.itemView.layoutParams=RecyclerView.LayoutParams(0,0)
//            }

      }
  }

class GraphDetailWeekGraphHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val tv_week = itemView.tv_week
    val tv_week_range = itemView.tv_range_week
    val barchart = itemView.barchart
    val params = itemView.layoutParams
    fun bind(data : GraphSingleWeekData, pos:Int){
        tv_week.text = when(pos){
            0-> "첫째주"
            1-> "둘째주"
            2-> "셋째주"
            3-> "넷째주"
            else -> "다섯째주"
        }

//        val format = SimpleDateFormat("MM월 dd일")
//        val week_range = format.format(data.start)+"~"+format.format(data.end)
        val week_range = data.start + "~" + data.end
        tv_week_range.text = week_range

        barchart.drawSingleGraph(itemView.context, data.inventory, 3)
    }

 }

