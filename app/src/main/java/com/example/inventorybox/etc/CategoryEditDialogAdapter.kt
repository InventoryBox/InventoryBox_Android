package com.example.inventorybox.etc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.CategorySetDialog
import com.example.inventorybox.R
import com.example.inventorybox.data.CategorySetInfo
import com.example.inventorybox.graph.getColorFromRes
import kotlinx.android.synthetic.main.item_category_set.view.*

class CategoryEditDialogAdapter (private val context: Context): RecyclerView.Adapter<CategorySetDialogHolder>() {

    var datas: MutableList<CategorySetInfo> = mutableListOf()

    lateinit var listener : CategoryEditDialog.CategoryClickListener
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
        holder.bind(datas[position], position==0)

        holder.itemView.setOnClickListener {
            holder.itemView.background = holder.itemView.context.getDrawable(R.drawable.rec9_yellow_blank)
            android.os.Handler().postDelayed({listener.onClick(datas[position])},50)

        }
    }
}
class CategorySetDialogHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    val tv = itemView.tv_item_category_set
    fun bind(data : CategorySetInfo, isFirst : Boolean){
        tv.text = data.name
        if(isFirst){
            itemView.background = itemView.context.getDrawable(R.drawable.graph_rec9_lightgrey)
            itemView.isEnabled=false
            tv.setTextColor(getColorFromRes(itemView.context, R.color.darkgrey))
        }else{
            itemView.isEnabled=true
            tv.setTextColor(getColorFromRes(itemView.context, R.color.text_black))
            itemView.background = itemView.context.getDrawable(R.drawable.rec9_lightgrey_blank)
        }
    }

}