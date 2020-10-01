package com.inventorybox.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.data.CategoryInfo
import com.inventorybox.inventorybox.fragment.GraphFragment
import kotlinx.android.synthetic.main.item_graph_category.view.*
import kotlin.math.roundToInt

class GraphCategoryRVAdapter(private val context: Context): RecyclerView.Adapter<GraphCategoryViewHolder>() {

  	var datas: MutableList<CategoryInfo> = mutableListOf()
    //현재 보여주고 있는 카테고리
    private var selected_pos = 0
    lateinit var listener : GraphFragment.CategoryClickListener

  	// xml file을 inflate한 후 viewHolder를 만든다.
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  GraphCategoryViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.item_graph_category, parent,false)
  		return GraphCategoryViewHolder(view)
      }

      override fun getItemCount(): Int {
  		 return datas.size
      }
  	  // viewholder의 항목을 구성하기 위해 호출된다.
      override fun onBindViewHolder(holder: GraphCategoryViewHolder, position: Int) {
          holder.bind(datas[position])

          if(selected_pos==position){
              holder.set_selected()
          }else{
              holder.set_unselected()
          }

          holder.itemView.setOnClickListener {
              selected_pos=position
              notifyDataSetChanged()
              listener.onClick(datas[position].categoryIdx)
          }
          if(position==0){
              val params = LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.WRAP_CONTENT,
                  LinearLayout.LayoutParams.WRAP_CONTENT
              )
              params.marginStart = dpToPx(holder.itemView.context, 16)
              params.marginEnd = dpToPx(holder.itemView.context, 8)
              holder.itemView.layoutParams = params
          }else if(position==itemCount-1) {
              val params = LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.WRAP_CONTENT,
                  LinearLayout.LayoutParams.WRAP_CONTENT
              )
              params.marginEnd = dpToPx(holder.itemView.context, 16)
//              params.marginStart = dpToPx(holder.itemView.context, 8)
              holder.itemView.layoutParams = params
          }
          else {
              val params = LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.WRAP_CONTENT,
                  LinearLayout.LayoutParams.WRAP_CONTENT
              )
              params.marginEnd = dpToPx(holder.itemView.context, 8)
              holder.itemView.layoutParams = params
          }

//          if(position==itemCount-1){
//              val params = LinearLayout.LayoutParams(
//                  LinearLayout.LayoutParams.WRAP_CONTENT,
//                  LinearLayout.LayoutParams.WRAP_CONTENT
//              )
//              params.marginEnd = dpToPx(holder.itemView.context, 16)
//              params.marginStart = dpToPx(holder.itemView.context, 4)
//              holder.itemView.layoutParams = params
//          }

      }
    fun set_listener(listener : GraphFragment.CategoryClickListener){
        this.listener = listener
    }

    fun dpToPx(context: Context, dp: Int): Int {
        val density: Float = context.resources
            .displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

  }
class  GraphCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(data : CategoryInfo){
        itemView.tv_category_name.text=data.name


    }
    //selected일 때 변화
    fun set_selected(){
        itemView.setBackgroundResource(R.drawable.graph_rec20_dark_grey)
        itemView.tv_category_name.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
    }
    //unselect일 때의 변화
    fun set_unselected(){
        itemView.setBackgroundResource(R.drawable.graph_rec20_grey_blank)
        itemView.tv_category_name.setTextColor(ContextCompat.getColor(itemView.context, R.color.darkgrey))
    }
}