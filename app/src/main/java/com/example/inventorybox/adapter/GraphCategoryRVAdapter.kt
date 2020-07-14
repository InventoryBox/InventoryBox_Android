package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.data.CategoryInfo
import com.example.inventorybox.fragment.GraphFragment
import kotlinx.android.synthetic.main.item_graph_category.view.*

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


      }
    fun set_listener(listener : GraphFragment.CategoryClickListener){
        this.listener = listener
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