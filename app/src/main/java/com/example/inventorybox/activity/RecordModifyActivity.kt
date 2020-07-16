package com.example.inventorybox.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.adapter.RecordModifyAdapter
import com.example.inventorybox.data.RecordModifyData
import kotlinx.android.synthetic.main.activity_record.img_back
import kotlinx.android.synthetic.main.activity_record.rv_record_cate
import kotlinx.android.synthetic.main.activity_record.tv_plus
import kotlinx.android.synthetic.main.activity_record_modify.*

class RecordModifyActivity : AppCompatActivity() {

    val recordModifyAdapter= RecordModifyAdapter(this)
    var datas = mutableListOf<RecordModifyData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_modify)

        rv_record_modify.adapter = recordModifyAdapter
        loadRecordAddDatas()

        img_back.setOnClickListener {
            finish()
        }

        tv_plus.setOnClickListener {
            val intent = Intent(this, RecordAddActivity::class.java)
            startActivity(intent)
        }

        //카테고리 선택 뷰
        val datas_cate= mutableListOf<String>("전체","액체류","파우더류","과일류","치킨류","라떼류")

        val category_adapter = RecordCategoryAdapter(this)
//        category_adapter.datas =
        rv_record_cate.adapter = category_adapter

    }

    private fun loadRecordAddDatas(){
        datas.apply {
            add(
                RecordModifyData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = 3
                )
            )

            add(
                RecordModifyData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = 3
                )
            )

            add(
                RecordModifyData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = 3
                )
            )

            add(
                RecordModifyData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = 3
                )
            )
        }

        recordModifyAdapter.datas = datas
        recordModifyAdapter.notifyDataSetChanged()

    }



}