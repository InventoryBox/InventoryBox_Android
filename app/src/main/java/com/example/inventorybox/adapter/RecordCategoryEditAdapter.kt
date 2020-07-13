package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.ViewHolder.RecordCategoryVH
import com.example.inventorybox.activity.RecordCateogyActivity
import com.example.inventorybox.data.RecordCategoryData
import kotlinx.android.synthetic.main.activity_category_edit.*
import kotlinx.android.synthetic.main.item_record_edit.*
import kotlinx.android.synthetic.main.item_record_edit.view.*

class RecordCategoryEditAdapter(private val context: Context) : RecyclerView.Adapter<RecordCategoryVH>() {
    var datas = mutableListOf<RecordCategoryData>()

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
        if(isAllSelected){
            holder.is_selected = true
        }
        holder.bind(datas[position])
        holder.itemView.checkBox.setOnClickListener {
            checkbox_all_listener.onClick()
        }

    }

}