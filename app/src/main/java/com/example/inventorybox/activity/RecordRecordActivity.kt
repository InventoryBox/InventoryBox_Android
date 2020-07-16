package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordAddAdapter
import com.example.inventorybox.adapter.RecordCategoryAdapter
import kotlinx.android.synthetic.main.activity_record.img_back
import kotlinx.android.synthetic.main.activity_record.rv_record_add
import kotlinx.android.synthetic.main.activity_record.rv_record_cate
import kotlinx.android.synthetic.main.activity_record.tv_plus

class RecordRecordActivity : AppCompatActivity() {

    val recordAddAdapter= RecordAddAdapter(this)
    //var datas = mutableListOf<RecordAddData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        rv_record_add.adapter = recordAddAdapter
        //loadRecordAddDatas()

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
        val datas_cate= mutableListOf<String>("전체","액체류","파우더류","과일류","치킨류","라떼류")

        val category_adapter = RecordCategoryAdapter(this)
//        category_adapter.datas = datas_cate
        rv_record_cate.adapter = category_adapter

    }

    /*private fun loadRecordAddDatas(){
        datas.apply {
            add(
                RecordAddData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = ""
                )
            )

            add(
                RecordAddData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = ""
                )
            )

            add(
                RecordAddData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = ""
                )
            )

            add(
                RecordAddData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = ""
                )
            )
        }

        recordAddAdapter.datas = datas
        recordAddAdapter.notifyDataSetChanged()

    }*/

}