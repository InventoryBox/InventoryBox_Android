package com.inventorybox.inventorybox.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.inventorybox.inventorybox.CategorySetDialog
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.data.CategorySetInfo
import com.inventorybox.inventorybox.data.RecordCategorySettingData
import com.inventorybox.inventorybox.data.RequestRecordItemAdd
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.et_unit

class RecordAddActivity : AppCompatActivity() {

    var current_noti = 0;
    var current_order = 0;

    var icon_idx = -1
    var icon_url = ""
    var category_idx = -1
    var category_name = ""
    val requestToServer = RequestToServer

    var datas = mutableListOf<RecordCategorySettingData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // 뒤로가기
        img_back.setOnClickListener{
            finish()
        }

        btn_iconsetting.setOnClickListener {
            val intent = Intent(this, RecordIconActivity::class.java)
            startActivityForResult(intent, 0)
        }

        btn_save.setOnClickListener {
            val name = et_name.text.toString()
            val unit = et_unit.text.toString()
            val alarmCnt = Integer.parseInt(et_noti_count.text.toString())
            val orderCnt = Integer.parseInt(et_order_count.text.toString())

            postRecordAddResponse(name, unit, alarmCnt, orderCnt)
        }


        val listener = object : CategorySetListener{
            override fun onSet(item: CategorySetInfo) {
                category_idx = item.categoryIdx
                category_name = item.name
                tv_category.text = category_name
            }
        }
        //카테고리 설정 클릭 이벤트
        tv_category.setOnClickListener{

            val dialog = CategorySetDialog()
            dialog.confirm_listener = listener
            dialog.show(supportFragmentManager, "categoryselect")

        }

        //발주 알림 개수 - 선택
        btn_noti_minus.setOnClickListener {
            //0보다 작을수 없음
            if(current_noti <= 0){
                current_noti == 0
            }else{
                if(et_noti_count.text.toString()!="") {
                    current_noti = Integer.parseInt(et_noti_count.text.toString())
                }
                --current_noti
                et_noti_count.setText((current_noti).toString())
            }
        }

        //발주 알림 개수 + 선택
        btn_noti_plus.setOnClickListener {
            if(et_noti_count.text.toString()!="") {
                current_noti = Integer.parseInt(et_noti_count.text.toString())
            }
            ++current_noti
            et_noti_count.setText((current_noti).toString())
        }

        //발주 수량 개수 - 선택
        btn_order_minus.setOnClickListener {
            //0보다 작을수 없음
            if(current_order <= 0){
                current_order == 0
            }else{
                if(et_order_count.text.toString()!="") {
                    current_order = Integer.parseInt(et_order_count.text.toString())
                }
                --current_order
                et_order_count.setText((current_order).toString())
            }
        }

        //발주 수량 개수 + 선택
        btn_order_plus.setOnClickListener {
            if(et_order_count.text.toString()!="") {
                current_order = Integer.parseInt(et_order_count.text.toString())
            }
            ++current_order
            et_order_count.setText((current_order).toString())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){

            icon_idx = data!!.getIntExtra("icon_idx", 0)
            icon_url = data!!.getStringExtra("icon_url")
            if(icon_url.isNullOrBlank()){
                icon_url = "null"
            }

            btn_iconsetting.setPadding(0,0,0,0)
            Glide.with(this).load(icon_url).into(btn_iconsetting)
        }
    }

    private fun postRecordAddResponse(name: String, unit: String, alarmCnt: Int, orderCnt: Int){
        requestToServer.service.postRecordAddResponse(
            SharedPreferenceController.getUserToken(this),
            RequestRecordItemAdd(
                name = name,
                unit = unit,
                alarmCnt = alarmCnt,
                memoCnt = orderCnt,
                iconIdx = icon_idx,
                categoryIdx = category_idx
            )
        ).customEnqueue(
            onSuccess = {
                finish()
            }
        )
    }

    interface CategorySetListener{
        fun onSet(item : CategorySetInfo)
    }
}
