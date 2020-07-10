package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.data.RecordCategoryData
import kotlinx.android.synthetic.main.activity_category_edit.*
import kotlinx.android.synthetic.main.fragment_record.*

class RecordCateogyActivity : AppCompatActivity() {

    val recordCategoryAdapter = RecordCategoryAdapter(this)
    var datas = mutableListOf<RecordCategoryData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_edit)

        rv_record_category_edit.adapter = recordCategoryAdapter
        loadRecordCategoryDatas()

        //버튼 클릭시 최상단으로 이동
        ll_up.setOnClickListener{
            scrollview_category_edit.smoothScrollTo(0, 0)
        }
    }

    //데이터 추가
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