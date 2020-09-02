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

class GraphDetailWeekCalAdapter(private val context: Context): RecyclerView.Adapter<GraphDetailWeekCalHolder>() {

//  	var datas: MutableList<String> = mutableListOf()
    lateinit var listener: onMyChangeListener

    var hasList : MutableList<Boolean> = mutableListOf()
    var cal = Calendar.getInstance()
    var max_week = cal.get(Calendar.WEEK_OF_MONTH)


    // xml file을 inflate한 후 viewHolder를 만든다.
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  GraphDetailWeekCalHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.item_graph_detail_calendar, parent,false)



        for (i in 1..7){
            hasList.add(i <=max_week)
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
    // 캘린더 눌렸을 때 그래프 visibility 변경하도록 하는 리스너
    fun set(listener : onMyChangeListener){
        this.listener=listener
    }


  }
class GraphDetailWeekCalHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    var isClicked =true
    val tv_week = itemView.tv_week
    lateinit var listener: onMyChangeListener

    // 클릭해서 graph의 visibility 설정 가능하도록
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
        //data 없으면 deactivate - 아예 안눌리도록
        if(!hasData){
            deactivate()
//            listener.onChange(adapterPosition, false)
            itemView.isEnabled = false
        }else{
            itemView.isEnabled = true
//            listener.onChange(adapterPosition, true)
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
        itemView.background.setTint(com.example.inventorybox.graph.getColorFromRes(itemView.context, R.color.middlegrey))
        tv_week.setTextColor(itemView.context.getColorFromRes(R.color.white))
        isClicked=false
    }
    fun activate(){
        itemView.background.setTint(com.example.inventorybox.graph.getColorFromRes(itemView.context, R.color.yellow))
        tv_week.setTextColor(itemView.context.getColorFromRes(R.color.white))
        isClicked=true
    }

 }