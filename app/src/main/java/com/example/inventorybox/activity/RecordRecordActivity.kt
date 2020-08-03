package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
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
        rv_cate_record_modify.adapter = category_adapter
        rv_item_record_modify.adapter = item_adapter

        // 카테고리 클릭 이벤트 받아올 리스너
        val category_listener = object : RecordFragment.CategoryClickListener {
            override fun onClick(category_idx: Int) {
                if (category_idx > 1) {
                    sorted_item = datas_item.filter {
                        it.categoryIdx == category_idx
                    }.toMutableList()
                } else {
                    sorted_item = datas_item
                }
                item_adapter.datas = sorted_item
                item_adapter.notifyDataSetChanged()

            }
        }

        category_adapter.listener = category_listener

        requestData()

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
            finish()
        }
    }

    private fun requestData(){

        RequestToServer.service.getRecordRecordRecord(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
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

                item_adapter.datas = datas_item
                item_adapter.notifyDataSetChanged()

                var recentDate = it.data.date
                record_modify_tv_date.setText(recentDate)
            })
    }

    fun putData(){

        var datas = arrayListOf<ResponseRecordCntItemInfo>()
        for (data in datas_item.filter { it.stocksCnt!=-1 }){
            datas.add(
                ResponseRecordCntItemInfo(
                    data.itemIdx,
                    data.stocksCnt
                )
            )
        }

        val cal : Calendar = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd")
//        cal_month.text=cal.get(Calendar.MONTH).toString()
        val today : String=format.format(cal.time)


        RequestToServer.service.requestRecordModify(
            getString(R.string.test_token),
            RequestRecordItemModify(
                today,
                datas
            )
        ).customEnqueue(
            onSuccess = {
                Log.d("####RecordRecordActivity","success")
                finish()
            }
        )
    }
    interface onItemCountChangeListener{
        fun onChange(itemIdx : Int, count : Int)
    }

}



//
//    var datas_item = mutableListOf<RecordRecordItemInfo>()
//    var item_list = hashMapOf<Int,Int>()
//    val requestToServer = RequestToServer
//    lateinit var recordRecordAdapter: RecordRecordAdapter
//
//    lateinit var category_adapter : RecordRecordCategoryAdapter
//    var datas_cate = mutableListOf<RecordRecordCategoryInfo>()
//
//    var datas = arrayListOf<ResponseRecordCntItemInfo>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_record)
//
//        val listener = object : OnMyClickListener{
//            override fun onChange(itemIdx: Int, presentCont: Int) {
//
//            }
//        }
//
//        recordRecordAdapter = RecordRecordAdapter(this)
//        rv_record_add.adapter = recordRecordAdapter
//        recordRecordAdapter.listener = listener
//
//        //데이터 가져오기
////        RecordRecordResponse()
//
//        //저장버튼 누르면 데이터 보내기
//        btn_record.setOnClickListener {
//            val value = Integer.parseInt(tv_rv_input_stock.text.toString())
//            //val position =
//
//            /*for(i in datas_item) {
//                RecordRecord(position, value)
//            }*/
//        }
//
//        //rv_record_add.setOverScrollMode(View.OVER_SCROLL_NEVER)
//
//        //뒤로가기 이미지 클릭
//        img_back.setOnClickListener {
//            finish()
//        }
//
//        //재료추가 텍스트 클릭
//        tv_plus.setOnClickListener {
//            val intent = Intent(this, RecordAddActivity::class.java)
//            startActivity(intent)
//        }
//
//        //카테고리 선택 뷰
//        category_adapter = RecordRecordCategoryAdapter(this)
//        category_adapter.datas = datas_cate
//        rv_record_cate.adapter = category_adapter
//
//        btn_record.setOnClickListener {
//
//
//            for (i in 0..recordRecordAdapter.itemCount-1){
//                val item_view = rv_record_add.layoutManager?.findViewByPosition(i)
//                var value = "-1"
//                try{value = item_view?.rv_record_add!!.tv_rv_input_stock.text.toString()}
//                catch (e : Exception){
//
//                }
//                item_list[datas_item[i].itemIdx] =if(value!=""&&value!=null){Integer.parseInt(value)} else -1
//            }
//
//            for ((key, value) in item_list){
//                datas.add(
//                    ResponseRecordCntItemInfo(
//                        key,value
//                    )
//                )
//            }
//
//            val cal : Calendar = Calendar.getInstance()
//            val format = SimpleDateFormat("yyyy-MM-dd")
////        cal_month.text=cal.get(Calendar.MONTH).toString()
//            val today : String=format.format(cal.time)
//
//
//            RequestToServer.service.requestRecordModify(
//                getString(R.string.test_token),
//                RequestRecordItemModify(
//                    today,
//                    datas
//                )
//            ).customEnqueue(
//                onSuccess = {
//                    Log.d("############","success")
//                    finish()
//                }
//            )
//        }
//
//    }
//
//    private fun RecordRecordResponse() {
//        requestToServer.service.getRecordRecordRecord(
//            getString(R.string.test_token)
//        ).customEnqueue(
//            onSuccess = {
//                datas_cate = mutableListOf()
//                for (data in it.data.categoryInfo) {
//                    datas_cate.add(data)
//                }
//                rv_record_cate.adapter = category_adapter
//                category_adapter.datas = datas_cate
//                category_adapter.notifyDataSetChanged()
//
//                datas_item = mutableListOf()
//                for (data in it.data.itemInfo) {
//                    datas_item.add(data)
//                    item_list[data.itemIdx] = -1
//                }
//
//                recordRecordAdapter.datas = datas_item
//                rv_record_add.adapter = recordRecordAdapter
//                recordRecordAdapter.notifyDataSetChanged()
//
//                var recentDate = it.data.date
//                tv_date.setText(recentDate)
//            })
//    }
//
//    override fun onResume() {
//        super.onResume()
//        RecordRecordResponse()
//    }
//    /*private fun RecordRecord(position:Int, value: Int) {
//        requestToServer.service.requestRecordModify(
//            getString(R.string.test_token),
//            ResponseRecordCntItemInfo(
//                itemIdx = position,
//                presentCnt = value
//            )
//        )
//    }*/
//
//}
//
//interface OnMyClickListener{
//    fun onChange(itemIdx : Int, presentCont : Int)
//}