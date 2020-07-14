package com.example.inventorybox.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.adapter.RecordCategoryEditAdapter
import com.example.inventorybox.data.RecordCategoryData
import kotlinx.android.synthetic.main.activity_category_edit.*


class RecordCateogyActivity : AppCompatActivity() {

    val recordCategoryAdapter = RecordCategoryEditAdapter(this)
    var datas = mutableListOf<RecordCategoryData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_edit)

        // checkbox 가 눌리면,
        val checkbox_click_listener = object : CheckboxClickListener{
            override fun onClick() {
                checkBox_all.isChecked = false
            }
        }
        recordCategoryAdapter.setListener(checkbox_click_listener)

        rv_record_category_edit.adapter = recordCategoryAdapter
        loadRecordCategoryDatas()

        //뒤로가기 버튼 누르면 화면 나가기
        img_back.setOnClickListener {
            finish()
       }


        //체크박스 선택시 전체 체크박스 선택되도록
        checkBox_all.setOnClickListener {
            if(checkBox_all.isChecked){
                recordCategoryAdapter.isAllSelected = true
                recordCategoryAdapter.notifyDataSetChanged()


            }
        }



        //카테고리 선택 뷰
        val datas_cate= mutableListOf<String>("전체","액체류","파우더류","과일류","치킨류","라떼류")

        val category_adapter = RecordCategoryAdapter(this)
        category_adapter.datas = datas_cate
        rv_record_cate.adapter = category_adapter

        //카테고리 추가 버튼 클릭 시 다이얼로그
        btn_add.setOnClickListener {
            val builder : AlertDialog.Builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.layout_add_custom_dialog, null)

            builder.setView(dialogView)
                .show()
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

    interface CheckboxClickListener{
        fun onClick()
    }
}