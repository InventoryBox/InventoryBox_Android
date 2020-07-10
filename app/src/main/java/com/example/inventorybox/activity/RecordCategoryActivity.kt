package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.data.RecordCategoryData
import kotlinx.android.synthetic.main.activity_category_edit.*

class RecordCateogyActivity : AppCompatActivity() {

    val recordCategoryAdapter = RecordCategoryAdapter(this)
    var datas = mutableListOf<RecordCategoryData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_edit)

        rv_record_category_edit.adapter = recordCategoryAdapter
        loadRecordCategoryDatas()

        img_back.setOnClickListener {
            finish()
        }

    }

    private fun loadRecordCategoryDatas(){
        datas.apply {
            add(
                RecordCategoryData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    unit = "덩어리",
                    count_noti = 500
                )
            )

            add(
                RecordCategoryData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    unit = "덩어리",
                    count_noti = 500
                )
            )

            add(
                RecordCategoryData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    unit = "덩어리",
                    count_noti = 500
                )
            )

            add(
                RecordCategoryData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    unit = "덩어리",
                    count_noti = 500
                )
            )
        }

        recordCategoryAdapter.datas = datas
        recordCategoryAdapter.notifyDataSetChanged()

    }


}