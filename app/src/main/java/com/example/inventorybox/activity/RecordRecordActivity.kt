package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.*
import com.example.inventorybox.data.RecordRecordCategoryInfo
import com.example.inventorybox.data.RecordRecordItemInfo
import com.example.inventorybox.data.RequestRecordItemModify
import com.example.inventorybox.data.ResponseRecordCntItemInfo
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.custonEnqueue
import kotlinx.android.synthetic.main.activity_record.img_back
import kotlinx.android.synthetic.main.activity_record.rv_record_add
import kotlinx.android.synthetic.main.activity_record.rv_record_cate
import kotlinx.android.synthetic.main.activity_record.tv_plus
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.item_record_record.*

class RecordRecordActivity : AppCompatActivity() {

    var datas_item = mutableListOf<RecordRecordItemInfo>()

    val requestToServer = RequestToServer
    lateinit var recordRecordAdapter: RecordRecordAdapter

    lateinit var category_adapter : RecordRecordCategoryAdapter
    var datas_cate = mutableListOf<RecordRecordCategoryInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        recordRecordAdapter = RecordRecordAdapter(this)
        rv_record_add.adapter = recordRecordAdapter

        //데이터 가져오기
        RecordRecordResponse()

        //저장버튼 누르면 데이터 보내기
        btn_record.setOnClickListener {
            val value = Integer.parseInt(tv_rv_input_stock.text.toString())
            //val position =

            /*for(i in datas_item) {
                RecordRecord(position, value)
            }*/
        }



        //뒤로가기 이미지 클릭
        img_back.setOnClickListener {
            finish()
        }

        //재료추가 텍스트 클릭
        tv_plus.setOnClickListener {
            val intent = Intent(this, RecordAddActivity::class.java)
            startActivity(intent)
        }

        //카테고리 선택 뷰
        category_adapter = RecordRecordCategoryAdapter(this)
        category_adapter.datas = datas_cate
        rv_record_cate.adapter = category_adapter

    }

    private fun RecordRecordResponse() {
        requestToServer.service.getRecordRecordRecord(
            getString(R.string.test_token)
        ).custonEnqueue(
            onSuccess = {
                for (data in it.data.categoryInfo) {
                    datas_cate.add(data)
                }
                category_adapter.datas = datas_cate
                category_adapter.notifyDataSetChanged()

                for (data in it.data.itemInfo) {
                    datas_item.add(data)
                }

                recordRecordAdapter.datas = datas_item
                recordRecordAdapter.notifyDataSetChanged()

                var recentDate = it.data.date
                tv_date.setText(recentDate)
            })
    }

    /*private fun RecordRecord(position:Int, value: Int) {
        requestToServer.service.requestRecordModify(
            getString(R.string.test_token),
            ResponseRecordCntItemInfo(
                itemIdx = position,
                presentCnt = value
            )
        )
    }*/

}