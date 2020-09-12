package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.inventorybox.DB.SharedPreferenceController
import com.example.inventorybox.R
import com.example.inventorybox.adapter.*
import com.example.inventorybox.data.*
import com.example.inventorybox.fragment.RecordFragment
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_record_modify.*
import kotlinx.android.synthetic.main.activity_record_modify.ll_up
import kotlinx.android.synthetic.main.fragment_graph.*
import kotlinx.android.synthetic.main.fragment_graph_detail.*
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.tv_date
import kotlinx.android.synthetic.main.item_record_record.*
import kotlinx.android.synthetic.main.item_record_record.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
// 오늘 재고기록하기
class RecordRecordActivity : AppCompatActivity() {


    lateinit var category_adapter: RecordCategoryAdapter
    lateinit var item_adapter: RecordModifyAdapter
    var datas_cate = mutableListOf<RecordHomeCategoryInfo>()
    var datas_item = mutableListOf<RecordModifyItemInfo>()
    var sorted_item = mutableListOf<RecordModifyItemInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_modify)

        // adapter 설정
        category_adapter = RecordCategoryAdapter(this)
        item_adapter = RecordModifyAdapter(this)

        rv_item_record_modify.adapter = item_adapter

        // 카테고리 클릭 이벤트 받아올 리스너
        val category_listener = object : RecordFragment.CategoryClickListener {
            override fun onClick(category_idx: Int) {

                //현재 입력된 count 저장

                if(sorted_item.count()!=0){
                    for(i in 0 until item_adapter.itemCount){
                        val item_view = rv_item_record_modify.layoutManager?.findViewByPosition(i)
                        val count = item_view?.findViewById<EditText>(R.id.tv_rv_input_stock)?.text.toString()

                        val idx = datas_item.indexOfFirst {
                            it.itemIdx==sorted_item[i].itemIdx
                        }
                        datas_item[idx].stocksCnt = if(count.isNotEmpty()) {
                            Integer.parseInt(count)
                        }else{
                            -1
                        }
                        Log.d("####record record activity", datas_item[idx].toString())
                    }
                }

//
                sorted_item = if (category_idx > 1) {
                    datas_item.filter {
                        it.categoryIdx == category_idx
                    }.toMutableList()
                } else {
                    datas_item
                }

                item_adapter.datas = sorted_item
                item_adapter.notifyDataSetChanged()
                Log.d("####record record activity", "category$category_idx")

            }
        }

        category_adapter.listener = category_listener
        rv_cate_record_modify.adapter = category_adapter
//        requestData()

        //버튼 눌렀을 때 최상단으로 이동
        ll_up.setOnClickListener {
            scrollview_record_modify.smoothScrollTo(0, 0)
        }

        scrollview_record_modify.setOverScrollMode(View.OVER_SCROLL_NEVER)


        //뒤로 가기
        img_back.setOnClickListener {
            finish()
        }

        //재료 추가하기
        btn_add_ingre.setOnClickListener {
            val intent = Intent(this, RecordAddActivity::class.java)
            startActivity(intent)
        }

        btn_confirm_record_modify.setOnClickListener {
            for(i in 0..sorted_item.size-1){
                val item_view = rv_item_record_modify.layoutManager?.findViewByPosition(i)
                val count = item_view?.findViewById<EditText>(R.id.tv_rv_input_stock)?.text.toString()

                val idx = datas_item.indexOfFirst {
                    it.itemIdx==sorted_item[i].itemIdx
                }
                datas_item[idx].stocksCnt = if(count.isNotEmpty()) {
                    Integer.parseInt(count)
                }else{
                    -1
                }
            }


            uploadData()
        }
    }

    private fun requestData(){


        RequestToServer.service.getRecordRecordRecord(
            SharedPreferenceController.getUserToken(this)
        ).customEnqueue(
            onSuccess = {
                sorted_item = mutableListOf()
                datas_item = mutableListOf()

                datas_cate = mutableListOf()
                for (data in it.data.categoryInfo) {
                    datas_cate.add(data)
                }
                category_adapter.datas = datas_cate
                category_adapter.notifyDataSetChanged()

                datas_item = mutableListOf()
                for (data in it.data.itemInfo) {
                    data.stocksCnt = -1
                    datas_item.add(data)
                }

                sorted_item = datas_item
                item_adapter.datas = sorted_item
                item_adapter.notifyDataSetChanged()

                var recentDate = it.data.date
                record_modify_tv_date.setText(recentDate)
            })
    }

    fun uploadData(){
        //오늘 날짜 가져오기
        val cal : Calendar = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd")
//        cal_month.text=cal.get(Calendar.MONTH).toString()
        val today : String=format.format(cal.time)



        var datas = arrayListOf<ResponseRecordCntItemInfo>()
//        for(i in 0..(item_adapter.itemCount-1)){
//            val itemView = rv_item_record_modify.layoutManager?.findViewByPosition(i)
//            val count = itemView?.findViewById<EditText>(R.id.tv_rv_input_stock)?.text.toString()
//            datas.add(
//                ResponseRecordCntItemInfo(
//                    datas_item[i].itemIdx,
//                    if(count.isNotEmpty()) {
//                        Integer.parseInt(count)
//                    }else{
//                        -1
//                    }
//                )
//            )
//        }

        for(item in datas_item){
            datas.add(
                    ResponseRecordCntItemInfo(
                            item.itemIdx,
                            item.stocksCnt
                    )
            )


        }
        RequestToServer.service.requestRecordModify(
            SharedPreferenceController.getUserToken(this),
            RequestRecordItemModify(
                today,
                datas
            )
        ).customEnqueue(
            onSuccess = {
                Log.d("RecordRecordActivity","success")
                Log.d("RecordRecordActivity",datas.toString())
                Log.d("RecordRecordActivity",today)

                finish()
            }
        )

    }

    override fun onResume() {
        super.onResume()
        requestData()
    }
}


