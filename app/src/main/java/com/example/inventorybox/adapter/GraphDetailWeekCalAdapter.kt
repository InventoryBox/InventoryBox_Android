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
import java.util.*

class GraphDetailWeekCalAdapter(private val context: Context, val max_week:Int): RecyclerView.Adapter<GraphDetailWeekCalHolder>() {

//  	var datas: MutableList<String> = mutableListOf()
    lateinit var listener: onMyChangeListener

    var hasList : MutableList<Boolean> = mutableListOf()


    // xml file을 inflate한 후 viewHolder를 만든다.
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  GraphDetailWeekCalHolder{
  	    val view = LayoutInflater.from(context).inflate(R.layout.item_graph_detail_calendar, parent,false)


        val cal = Calendar.getInstance()
        val week = cal.get(Calendar.WEEK_OF_MONTH)

        for (i in 1..6){
            hasList.add(i <=week)
        }
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
              4->"다섯째주"
              else->"여섯째주"
          }
          holder.bind(week_name, listener, hasList[position])
      }
    fun set(listener : onMyChangeListener){
        this.listener=listener
    }
  }
class GraphDetailWeekCalHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    var isClicked =true
    val tv_week = itemView.tv_week
    lateinit var listener: onMyChangeListener


    init {
        itemView.setOnClickListener {
            if(isClicked){
                backgroundToGrey()
                listener.onChange(adapterPosition, false)
                isClicked = false
            }else{
                backgroundToYellow()
                listener.onChange(adapterPosition, true)
                isClicked = true
            }
        }
    }

    fun bind(data : String, listener: onMyChangeListener, hasData : Boolean){
        tv_week.text = data
        this.listener = listener
        //data 없으면 deactivate
        if(!hasData){
            deactivate()
            listener.onChange(adapterPosition, false)
            itemView.isClickable = false
        }else{
            itemView.isClickable = true
            activate()
        }

    }
    //background 노란색으로 바꾸는
    fun backgroundToYellow(){
        itemView.setBackgroundResource(R.drawable.graph_rec9_yellow)
        tv_week.setTextColor(itemView.context.getColorFromRes(R.color.white))
    }
    fun backgroundToGrey(){
        itemView.setBackgroundResource(R.drawable.graph_rec9_white)
        tv_week.setTextColor(itemView.context.getColorFromRes(R.color.darkgrey))
    }

    fun deactivate(){
        itemView.setBackgroundResource(R.drawable.graph_rec9_whitegrey)
        tv_week.setTextColor(itemView.context.getColorFromRes(R.color.middlegrey))
        isClicked=false
    }
    fun activate(){
        itemView.setBackgroundResource(R.drawable.graph_rec9_yellow)
        tv_week.setTextColor(itemView.context.getColorFromRes(R.color.white))
        isClicked=true
    }

 }