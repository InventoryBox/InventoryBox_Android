package com.example.inventorybox.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.adapter.RecordCategoryEditAdapter
import com.example.inventorybox.data.*
import com.example.inventorybox.etc.CategoryEditDialog
import com.example.inventorybox.fragment.RecordFragment
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_category_edit.*
import kotlinx.android.synthetic.main.activity_category_edit.img_back
import kotlinx.android.synthetic.main.layout_custom_category.*
import kotlinx.android.synthetic.main.layout_custom_toast.view.*
import java.util.*


class RecordCateogyActivity : AppCompatActivity() {

    var item_adapter = RecordCategoryEditAdapter(this)
    //deleted pos에 onClick에 추가한 itemindex를 배열로 보내주기
    lateinit var category_adapter : RecordCategoryAdapter



    var datas = mutableListOf<RecordHomeCategoryInfo>()
    var datas_item = mutableListOf<RecordHomeItemInfo>()
    var sorted_item = mutableListOf<RecordHomeItemInfo>()
    var datas_cate = mutableListOf<RecordHomeCategoryInfo>()

    // 클릭된 아이템의 position
    var clicked_pos = mutableListOf<Int>()

    //    var item_index = mutableListOf<Int>()
    // 클릭된 아이템의 idx
    var clicked_idx = mutableListOf<Int>()

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_edit)

//        val date = intent.getStringExtra("date")
        val date = "0"
        //상단 카테고리
        category_adapter = RecordCategoryAdapter(this)
        // 카테고리 클릭 이벤트 리스너
        val category_listener = object : RecordFragment.CategoryClickListener {
            override fun onClick(category_idx: Int) {
                if(category_idx>1){
                    sorted_item = datas_item.filter {
                        it.categoryIdx == category_idx
                    }.toMutableList()
                }else{
                    sorted_item = datas_item
                }
                item_adapter.datas = sorted_item
                item_adapter.notifyDataSetChanged()

            }
        }
        category_adapter.listener = category_listener
        category_adapter.datas = datas_cate
        rv_category_record_cate.adapter = category_adapter




        rv_item_record_cate.adapter = item_adapter

        item_adapter.datas = datas_item
        item_adapter.notifyDataSetChanged()


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

//            for(i in clicked_pos){
//                datas_item.removeAt(i)
//            }
            Log.d("RecordCategoryActivity",clicked_idx.toString())
            deleteRecordItem()
            clicked_idx = mutableListOf()
            clicked_pos = mutableListOf()
//            recordCategoryAdapter = RecordCategoryEditAdapter(this)
            requestData(date)

        }

        item_adapter.setListener(checkbox_click_listener)
//        recordCategoryAdapter.setListener(checkbox_click_listener2)


        rv_item_record_cate.adapter = item_adapter
        //loadRecordCategoryDatas()

        //뒤로가기 버튼 누르면 화면 나가기
        img_back.setOnClickListener {
            finish()
       }

        //체크박스 선택시 전체 체크박스 선택되도록
        checkBox_all.setOnClickListener {
            if(checkBox_all.isChecked){
                item_adapter.isAllSelected = true
                item_adapter.notifyDataSetChanged()
                clicked_idx.addAll(sorted_item.map { it.itemIdx })
                Log.d("####RecordCategoryActivity",clicked_idx.toString())
                clicked_idx.distinct()
            }
        }

        /*// 클릭 시 아직 준비중입니다 토스트
        btn_move.setOnClickListener {
            showToast(this, "아직 준비중입니다")
        }*/
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

                if(category_name.text.toString().isNotEmpty()){
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
                }

                dialog.dismiss()
            }

            val btn_negative = dialogView.findViewById<Button>(R.id.btn_negative)
            btn_negative.setOnClickListener{
                dialog.dismiss()
            }

        }

        // 카테고리 이동 버튼 클릭 시
        btn_move.setOnClickListener {

            val listener = object : RecordAddActivity.CategorySetListener {
                override fun onSet(item: CategorySetInfo) {
                    val category_idx = item.categoryIdx
                    requestCategoryMove(category_idx)
                }
            }

            val dialog = CategoryEditDialog()
            dialog.confirm_listener=listener
            dialog.title = "카테고리 이동"
            dialog.show(supportFragmentManager, null)


        }

        // 카테고리 삭제 버튼 클릭 시
        btn_folder_delete.setOnClickListener {
            
            val listener = object : RecordAddActivity.CategorySetListener {
                override fun onSet(item: CategorySetInfo) {
                    var category_idx = item.categoryIdx
                    requestCategoryDelete(category_idx)
                }
            }

            val dialog = CategoryEditDialog()
            dialog.confirm_listener=listener
            dialog.title = "카테고리 삭제"
            dialog.show(supportFragmentManager, null)
        }

    }

    private fun requestCategoryDelete(categoryIdx: Int) {
        requestToServer.service.deleteCategory(
            getString(R.string.test_token),
            categoryIdx
        ).customEnqueue(
            onSuccess = {
                datas_cate.filter{
                    it.categoryIdx!=categoryIdx
                }
                recreate()

            }
        )
    }

    private fun requestCategoryMove(categoryIdx: Int) {
        var list = mutableListOf<CategoryMove>()
        for(i in clicked_idx){
            list.add(CategoryMove(i, categoryIdx))

        }
        requestToServer.service.moveCategory(
            getString(R.string.test_token),
            RequestRecordDelete(list)
        ).customEnqueue(
            onSuccess ={
                Log.d("recordcategory move","${clicked_idx.toString()} move to ${categoryIdx}")
                recreate()
            }
        )
    }


    // data 가져옴
    // default 값으로?
    fun requestData(date: String){

        Log.d("#############",date)
        datas_cate = mutableListOf()
        datas_item = mutableListOf()
        requestToServer.service.getRecordHomeResponse(
            "0", getString(R.string.test_token)
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
                sorted_item =datas_item
                item_adapter.datas = sorted_item
                item_adapter.notifyDataSetChanged()

            }
        )
    }

    private fun deleteRecordItem(){
        Log.d("recordcategory delete","${clicked_idx.toString()} deleted")
        requestToServer.service.deleteRecord(
            getString(R.string.test_token),
            clicked_idx.toString()
//        RequestRecordDelete(
//            clicked_idx
//        )
        ).customEnqueue(
            onSuccess = {
                Log.d("recordcategory delete","${clicked_idx.toString()} deleted")
            }
        )
    }

    interface CheckboxClickListener{
        fun onClick(idx : Int, pos : Int, isClicked : Boolean)
    }


    fun showToast(context: Context, message : String){
        val inflater: LayoutInflater = LayoutInflater.from(context)


        val toast_view : View = inflater.inflate(R.layout.layout_custom_toast, null)

        toast_view.toast_message.text=message
        val toast= Toast(context)
        toast.view=toast_view
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
        toast.setGravity(Gravity.BOTTOM,0,300)

    }
}