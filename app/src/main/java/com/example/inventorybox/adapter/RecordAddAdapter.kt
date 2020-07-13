package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.ViewHolder.RecordAddVH
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.data.RecordAddData
import net.cachapa.expandablelayout.ExpandableLayout


class RecordAddAdapter(private val context: Context) : RecyclerView.Adapter<RecordAddVH>() {
    var datas = mutableListOf<RecordAddData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAddVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_record, parent, false)
        return RecordAddVH(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecordAddVH, position: Int) {
        holder.bind(datas[position])

    }

}
