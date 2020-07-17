package com.example.inventorybox.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.adapter.RecordModifyAdapter
import com.example.inventorybox.adapter.RecordModifyCategoryAdapter
import com.example.inventorybox.data.*
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.activity_record.img_back
import kotlinx.android.synthetic.main.activity_record.rv_record_cate
import kotlinx.android.synthetic.main.activity_record.tv_plus
import kotlinx.android.synthetic.main.activity_record_modify.*
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.item_record_record.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class RecordModifyActivity : AppCompatActivity(){

    //var datas = mutableListOf<RecordModifyData>()

    var item_list = hashMapOf<Int,Int>()
    var datas_item = mutableListOf<RecordModifyItemInfo>()
    var datas = arrayListOf<ResponseRecordCntItemInfo>()
    val requestToServer = RequestToServer
    lateinit var recordModifyAdapter: RecordModifyAdapter

    lateinit var category_adapter : RecordModifyCategoryAdapter
    var datas_cate = mutableListOf<RecordModifyCategoryInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_modify)

        recordModifyAdapter = RecordModifyAdapter(this)
        rv_record_modify.adapter = recordModifyAdapter



        //데이터 가져오기
        RecordModifyResponse()

        img_back.setOnClickListener {
            finish()
        }

        tv_plus.setOnClickListener {
            val intent = Intent(this, RecordAddActivity::class.java)
            startActivity(intent)
        }

        //카테고리 선택 뷰
        category_adapter = RecordModifyCategoryAdapter(this)
        category_adapter.datas = datas_cate
        rv_record_cate.adapter = category_adapter

        btn_save.setOnClickListener {

            for (i in 0..recordModifyAdapter.itemCount-1){
                val item_view = rv_record_modify.layoutManager?.findViewByPosition(i)
                var value = "-1"
                try{value = item_view?.tv_rv_input_stock!!.tv_rv_input_stock.text.toString()}
                catch (e : Exception){

                }
                item_list[datas_item[i].itemIdx] =if(value!=""&&value!=null){Integer.parseInt(value)} else -1
            }

            for ((key, value) in item_list){
                datas.add(
                    ResponseRecordCntItemInfo(
                        key,value
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
                    Log.d("############","success")
                    finish()
                }
            )
        }

    }

    private fun RecordModifyResponse(){
        requestToServer.service.getRecordModifyResponse(
            "2020-07-17", getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                for(data in it.data.categoryInfo){
                    datas_cate.add(data)
                }
                category_adapter.datas = datas_cate
                category_adapter.notifyDataSetChanged()

                for(data in it.data.itemInfo){
                    datas_item.add(data)
                }
                recordModifyAdapter.datas = datas_item
                recordModifyAdapter.notifyDataSetChanged()
            })
    }
}