package com.example.inventorybox.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.adapter.RecordCategoryEditAdapter
import com.example.inventorybox.data.RecordHomeCategoryInfo
import com.example.inventorybox.data.RecordHomeItemInfo
import com.example.inventorybox.data.RequestCategoryAdd
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_category_edit.*
import kotlinx.android.synthetic.main.activity_category_edit.rv_record_cate
import kotlinx.android.synthetic.main.fragment_record.*
import java.util.*


class RecordCateogyActivity : AppCompatActivity() {

    var item_rv_adapter = RecordCategoryEditAdapter(this)
    var datas = mutableListOf<RecordHomeCategoryInfo>()
    var datas_item = mutableListOf<RecordHomeItemInfo>()
    var clicked_pos = mutableListOf<Int>()
//    var item_index = mutableListOf<Int>()
    var clicked_idx = mutableListOf<Int>()
    //deleted pos에 onClick에 추가한 itemindex를 배열로 보내주기

    var datas_cate = mutableListOf<RecordHomeCategoryInfo>()
    lateinit var category_adapter : RecordCategoryAdapter

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_edit)

        val date = intent.getStringExtra("date")

        //상단 카테고리
        category_adapter = RecordCategoryAdapter(this)
        category_adapter.datas = datas_cate
        rv_record_cate.adapter = category_adapter


        datas_item.add(
            RecordHomeItemInfo(
                1,1,"h",1,"apple",1,"u"
            )
        )
        rv_record_category_edit.adapter = item_rv_adapter
        item_rv_adapter.datas = datas_item


        requestData(date)
//        RecordCategoryResponse(date)

        // recyclerview 설정


        // (전체선택)checkbox 가 눌리면,
        val checkbox_click_listener = object : CheckboxClickListener{
            override fun onClick(idx: Int, pos: Int, isClicked : Boolean) { //deleted pos에 onClick에 추가한 itemindex를 배열로 보내주기
                checkBox_all.isChecked = false
                if(isClicked){
                    clicked_pos.add(pos)
//                    item_index.add(pos)
                    clicked_idx.add(idx)
                }else{
                    clicked_pos.remove(pos)
//                    item_index.remove(pos)
                    clicked_idx.remove(idx)
                }
            }
        }


        btn_delete.setOnClickListener {
            Collections.sort(clicked_pos)
            Collections.reverse(clicked_pos)

            for(i in clicked_pos){
                datas_item.removeAt(i)
            }
            deleteRecordItem()
            clicked_idx = mutableListOf()
            clicked_pos = mutableListOf()
//            recordCategoryAdapter = RecordCategoryEditAdapter(this)
            item_rv_adapter.datas = datas_item
            item_rv_adapter.notifyDataSetChanged()

        }

        item_rv_adapter.setListener(checkbox_click_listener)
//        recordCategoryAdapter.setListener(checkbox_click_listener2)


        rv_record_category_edit.adapter = item_rv_adapter
        //loadRecordCategoryDatas()

        //뒤로가기 버튼 누르면 화면 나가기
        img_back.setOnClickListener {
            finish()
       }

        //체크박스 선택시 전체 체크박스 선택되도록
        checkBox_all.setOnClickListener {
            if(checkBox_all.isChecked){
                item_rv_adapter.isAllSelected = true
                item_rv_adapter.notifyDataSetChanged()

            }
        }

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

                RequestToServer.service.requestCategoryAdd(
                    getString(R.string.test_token),
                    RequestCategoryAdd(
                        category_name.text.toString()
                    )
                ).customEnqueue(
                    onSuccess = {
                        Log.d("#######","category add success")
                        datas_cate.add(RecordHomeCategoryInfo(
                            datas_cate.size, category_name.text.toString()
                        ))
                        category_adapter.datas = datas_cate
                        category_adapter.notifyDataSetChanged()
                        
                    }
                )
                dialog.dismiss()
            }

            val btn_negative = dialogView.findViewById<Button>(R.id.btn_negative)
            btn_negative.setOnClickListener{
                dialog.dismiss()
            }

        }

    }
//
//    private fun RecordCategoryResponse(date : String){
//
//
//        requestToServer.service.getRecordHomeResponse(
//            date, getString(R.string.test_token)
//        ).customEnqueue(
//            onSuccess = {
//
//
//                Log.d("recordcategoryactivity1111",it.data.toString())
//                for(data in it.data.categoryInfo){
//                    datas_cate.add(data)
//                }
//                for(data in it.data.itemInfo){
//                    datas_item.add(data)
//                }
//                category_adapter.datas = datas_cate
//                category_adapter.notifyDataSetChanged()
//
//                item_rv_adapter.datas = datas_item
//                item_rv_adapter.notifyDataSetChanged()
//                Log.d("recordcategoryactivity",datas_item.toString())
//                Log.d("recordcategoryactivity",datas_cate.toString())
//            }
//        )
//
//
//    }


    fun requestData(date: String){

        Log.d("#############",date)
        datas_cate = mutableListOf()
        datas_item = mutableListOf()
        requestToServer.service.getRecordHomeResponse(
            date, getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {

                Log.d("#########", it.data.toString())

                for(data in it.data.categoryInfo){
                    datas_cate.add(data)
                }
                category_adapter.datas = datas_cate
                category_adapter.notifyDataSetChanged()

                for(data in it.data.itemInfo){
                    datas_item.add(data)

                }

                item_rv_adapter.datas = datas_item
                item_rv_adapter.notifyDataSetChanged()

            }
        )
    }

    private fun deleteRecordItem(){
        requestToServer.service.deleteRecord(
            getString(R.string.test_token),
            clicked_idx
        ).customEnqueue(
            onSuccess = {
                Log.d("recordcategory delete","success")
            }
        )
    }

    interface CheckboxClickListener{
        fun onClick(idx : Int, pos : Int, isClicked : Boolean)
    }
}