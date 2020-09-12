package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.activity.RecordCateogyActivity
import com.example.inventorybox.data.RecordHomeCategoryInfo
import com.example.inventorybox.data.RecordHomeItemInfo
import kotlinx.android.synthetic.main.item_record_category.view.*
import kotlinx.android.synthetic.main.item_record_edit.view.*


class RecordCategoryEditAdapter(private val context: Context) : RecyclerView.Adapter<RecordCategoryVH>() {
    var datas = mutableListOf<RecordHomeItemInfo>()

    // 전체 선택이 눌렸는 지
    var isAllSelected = false
    lateinit var checkbox_all_listener : RecordCateogyActivity.CheckboxClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordCategoryVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_edit, parent, false)
        return RecordCategoryVH(
            view
        )

    }

    override fun getItemCount(): Int {
        return datas.size
    }
    fun setListener(listener: RecordCateogyActivity.CheckboxClickListener){
        checkbox_all_listener = listener
    }

    override fun onBindViewHolder(holder: RecordCategoryVH, position: Int) {
//        if(isAllSelected){
//            holder.set_selected()
//        }
        holder.bind(datas.get(position))
        holder.itemView.checkBox.setOnClickListener {

            if(holder.itemView.checkBox.isChecked){
                checkbox_all_listener.onClick(datas[position].itemIdx, position,true)
            }else{
                checkbox_all_listener.onClick(datas[position].itemIdx, position,false)
            }
        }

        holder.itemView.setOnClickListener {
            holder.itemView.checkBox.performClick()
        }
    }

}

class  RecordCategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView){

    val checkbox = itemView.checkBox
    fun bind(data : RecordHomeItemInfo){
        itemView.tv_rv_product.text=data.name
        itemView.tv_rv_count_noti.setText(data.alarmCnt.toString())
        checkbox.isChecked=data.isSelected
        Glide.with(itemView.context).load(data.img).into(itemView.img_rv_product)
    }
    //selected일 때 변화
    fun set_selected(){
        checkbox.isChecked = true
    }


}