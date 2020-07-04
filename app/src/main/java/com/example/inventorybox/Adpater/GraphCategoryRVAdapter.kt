package com.example.inventorybox.Adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.item_graph_category.view.*

class GraphCategoryRVAdapter(private val context: Context): RecyclerView.Adapter<GraphCategoryViewHolder>() {

  	var datas: MutableList<String> = mutableListOf()

    //현재 보여주고 있는 카테고리
    var current_choice :Int = 0

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
          holder.adapterPosition
      }

  }
class  GraphCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var isSelected = false
    fun bind(data : String){
        itemView.tv_category_name.text=data
        //클릭되었을 때 색 바뀌기
        itemView.setOnClickListener {

//            if (pos!=RecyclerView.NO_POSITION){
                if(!isSelected){
                    itemView.setBackgroundResource(R.drawable.graph_rec20_dark_grey)
                    itemView.tv_category_name.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))

                }else{

                    itemView.setBackgroundResource(R.drawable.graph_rec20_grey_blank)
                    itemView.tv_category_name.setTextColor(ContextCompat.getColor(itemView.context, R.color.darkgrey))
                }
//            }
        }
    }
 }