package com.inventorybox.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.data.RecordRecordCategoryInfo
import kotlinx.android.synthetic.main.item_record_category.view.*

class RecordRecordCategoryAdapter(private val context: Context): RecyclerView.Adapter<RecordRecordCategoryViewHolder>() {

    var datas: MutableList<RecordRecordCategoryInfo> = mutableListOf()

    private var selected_pos = 0
    //현재 보여주고 있는 카테고리
    var current_choice :Int = 0
//    var selectedPos:Int = RecyclerView.NO_POSITION

    // xml file을 inflate한 후 viewHolder를 만든다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordRecordCategoryViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_category, parent,false)
        return RecordRecordCategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }
    // viewholder의 항목을 구성하기 위해 호출된다.
    override fun onBindViewHolder(holder: RecordRecordCategoryViewHolder, position: Int) {
        holder.bind(datas[position])

        if(selected_pos==position){
            holder.set_selected()
        }else{
            holder.set_unselected()
        }

        holder.itemView.setOnClickListener {
            selected_pos=position
            notifyDataSetChanged()
        }


    }

}
class  RecordRecordCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(data : RecordRecordCategoryInfo){
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