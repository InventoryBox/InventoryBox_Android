package com.inventorybox.inventorybox.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.activity.HomeOrderEditActivity
import com.inventorybox.inventorybox.data.HomeOrderData
import com.inventorybox.inventorybox.graph.draw5DaysGraph
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.item_home_edit_memo.view.*
import net.cachapa.expandablelayout.ExpandableLayout

class HomeOrderEditAdapter(private val context: Context) : RecyclerView.Adapter<HomeOrderEditViewHolder>() {
    var datas = mutableListOf<HomeOrderData>()

    lateinit var listener : HomeOrderEditActivity.CountChangeListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOrderEditViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_edit_memo, parent, false)
        return HomeOrderEditViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomeOrderEditViewHolder, position: Int) {
        holder.bind(datas[position])


        holder.plus.setOnClickListener {
            holder.increment()
        }

        holder.minus.setOnClickListener {
            holder.decrement()
        }

        holder.btn_more.setOnClickListener {
            holder.more()
        }

        holder.count.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val a = if (s.toString() == "") {
                    0
                } else {
                    Integer.parseInt(s.toString())
                }

                listener.onChange(
                    datas[position].itemIdx, a
                )
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

    }

    fun set_listener(listener: HomeOrderEditActivity.CountChangeListener){
        this.listener = listener

    }

}

class HomeOrderEditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val index = itemView.findViewById<ConstraintLayout>(R.id.rv_home_container)
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val count = itemView.findViewById<EditText>(R.id.tv_rv_count_noti)

    val plus = itemView.findViewById<ImageView>(R.id.iv_plus)
    val minus = itemView.findViewById<ImageView>(R.id.iv_minus)

    val btn_more = itemView.findViewById<ImageButton>(R.id.edit_btn_rv_more)
    val expandable = itemView.findViewById<ExpandableLayout>(R.id.edit_expandable_layout)

    val chart : BarChart = itemView.item_main_graph_chart_memo

    fun bind(homeData: HomeOrderData){
        Glide.with(itemView).load(homeData.img).into(img)
        name.text = homeData.itemName
        count.setText(homeData.memoCnt.toString())

        chart.draw5DaysGraph(itemView.context, homeData.stocksInfo, 4, homeData.alarmCnt)

    }

    //+버튼 눌렀을 때 숫자 증가
    fun increment(){
        var current = Integer.parseInt(count.getText().toString())
        count.setText((++current).toString())
    }

    //-버튼 눌렀을 때 숫자 감소
    fun decrement(){
        var current = Integer.parseInt(count.getText().toString())

        //0보다 작을수 없음
        if(current <= 0){
            current == 0
        }else{
            count.setText((--current).toString())
        }
    }

    //expandable layout 이벤트
    fun more(){
        expandable.toggle()
    }
}
