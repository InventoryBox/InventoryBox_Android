package com.example.inventorybox.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.adapter.RecordModifyAdapter
import com.example.inventorybox.adapter.RecordModifyCategoryAdapter
import com.example.inventorybox.data.RecordHomeCategoryInfo
import com.example.inventorybox.data.RecordModifyCategoryInfo
import com.example.inventorybox.data.RecordModifyItemInfo
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_record.img_back
import kotlinx.android.synthetic.main.activity_record.rv_record_cate
import kotlinx.android.synthetic.main.activity_record.tv_plus
import kotlinx.android.synthetic.main.activity_record_modify.*
import kotlinx.android.synthetic.main.fragment_record.*

class RecordModifyActivity : AppCompatActivity() {

    //var datas = mutableListOf<RecordModifyData>()

    var datas_item = mutableListOf<RecordModifyItemInfo>()

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

    }

    private fun RecordModifyResponse(){
        requestToServer.service.getRecordModifyResponse(
            "2020-07-18", getString(R.string.test_token)
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