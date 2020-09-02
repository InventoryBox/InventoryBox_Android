package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.CategorySetDialog
import com.example.inventorybox.R
import com.example.inventorybox.data.CategorySetInfo
import kotlinx.android.synthetic.main.item_category_set.view.*
import java.util.logging.Handler

class CategorySetDialogAdapter (private val context: Context): RecyclerView.Adapter<CategorySetDialogHolder>() {

  	var datas: MutableList<CategorySetInfo> = mutableListOf()

    lateinit var listener : CategorySetDialog.CategoryClickListener
  	// xml file을 inflate한 후 viewHolder를 만든다.
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  CategorySetDialogHolder{
  	    val view = LayoutInflater.from(context).inflate(R.layout.item_category_set, parent,false)
  		return CategorySetDialogHolder(view)
      }

      override fun getItemCount(): Int {
  		 return datas.size
      }
  	  // viewholder의 항목을 구성하기 위해 호출된다.
      override fun onBindViewHolder(holder: CategorySetDialogHolder, position: Int) {
          holder.bind(datas[position])

          holder.itemView.setOnClickListener {
              holder.itemView.background = holder.itemView.context.getDrawable(R.drawable.rec9_yellow_blank)
              android.os.Handler().postDelayed({listener.onClick(datas[position])},50)

          }
      }
  }
class CategorySetDialogHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    val tv = itemView.tv_item_category_set
    fun bind(data : CategorySetInfo){
        tv.text = data.name
    }
 }