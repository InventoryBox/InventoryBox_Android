package com.inventorybox.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.ViewHolder.RecordIconVH
import com.inventorybox.inventorybox.activity.RecordIconActivity
import com.inventorybox.inventorybox.data.RecordAddIconInfo

class RecordIconAdapter(private val context: Context) : RecyclerView.Adapter<RecordIconVH>() {
    var datas = mutableListOf<RecordAddIconInfo>()

    lateinit var listener : RecordIconActivity.IconClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordIconVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_icon, parent, false)
        return RecordIconVH(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecordIconVH, position: Int) {
        holder.bind(datas[position])
        holder.itemView.setOnClickListener {
            listener.onClick(datas[position].iconIdx, datas[position].img)
        }
    }

}