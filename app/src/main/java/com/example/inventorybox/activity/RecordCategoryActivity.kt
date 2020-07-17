package com.example.inventorybox.activity

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.adapter.RecordCategoryEditAdapter
import com.example.inventorybox.data.*
import com.example.inventorybox.network.RequestToServer

import kotlinx.android.synthetic.main.activity_category_edit.*
import java.util.*


class RecordCateogyActivity : AppCompatActivity() {

    var recordCategoryAdapter = RecordCategoryEditAdapter(this)
    var datas = mutableListOf<RecordHomeCategoryInfo>()
    var clicked_pos = mutableListOf<Int>()
    var item_index = mutableListOf<Int>()
    //deleted pos에 onClick에 추가한 itemindex를 배열로 보내주기

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_edit)

        // (전체선택)checkbox 가 눌리면,
        val checkbox_click_listener = object : CheckboxClickListener{
            override fun onClick(pos: Int, isClicked : Boolean) { //deleted pos에 onClick에 추가한 itemindex를 배열로 보내주기
                checkBox_all.isChecked = false
                if(isClicked){
                    clicked_pos.add(pos)
                    item_index.add(pos)
                }else{
                    clicked_pos.remove(pos)
                    item_index.remove(pos)
                }
            }
        }

        //deleteRecordItem()

        btn_delete.setOnClickListener {
            Collections.sort(clicked_pos)
            Collections.reverse(clicked_pos)

            for(i in clicked_pos){
                datas.removeAt(i)
            }
            clicked_pos = mutableListOf()
//            recordCategoryAdapter = RecordCategoryEditAdapter(this)
            recordCategoryAdapter.datas = datas
            recordCategoryAdapter.notifyDataSetChanged()

        }

        recordCategoryAdapter.setListener(checkbox_click_listener)
//        recordCategoryAdapter.setListener(checkbox_click_listener2)


        rv_record_category_edit.adapter = recordCategoryAdapter
        //loadRecordCategoryDatas()

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
        category_adapter.datas = datas
        rv_record_cate.adapter = category_adapter

        //카테고리 추가 버튼 클릭 시 다이얼로그
        btn_add.setOnClickListener {
            val builder : AlertDialog.Builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.layout_add_custom_dialog, null)

            builder.setView(dialogView)
            val dialog = builder.create()
            dialog.show()

            val btn_positive = dialogView.findViewById<Button>(R.id.btn_positive)
            btn_positive.setOnClickListener {
                val category_name = dialogView.findViewById<EditText>(R.id.et_category_name)
                datas_cate.add(datas_cate.size, category_name.getText().toString())
                dialog.dismiss()
            }

            val btn_negative = dialogView.findViewById<Button>(R.id.btn_negative)
            btn_negative.setOnClickListener{
                dialog.dismiss()
            }

        }

    }

    /*private fun deleteRecordItem(){
        requestToServer.service.deleteRecord(
            getString(R.string.test_token),
            item_index
        ).custonEnqueue(
            onSuccess = {
                Log.d("recordcategory delete","success")
            }
        )
    }*/

    interface CheckboxClickListener{
        fun onClick(pos : Int, isClicked : Boolean)
    }
}