package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.inventorybox.CategorySetDialog
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategorySettingAdapter
import com.example.inventorybox.data.CategorySetInfo
import com.example.inventorybox.data.RecordCategorySettingData
import com.example.inventorybox.data.RequestRecordItemAdd
import com.example.inventorybox.fragment.DialogFragment
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_add.*

class RecordAddActivity : AppCompatActivity() {

    var current_noti = 0;
    var current_order = 0;

    var icon_idx = -1
    var icon_url = ""
    var category_idx = -1
    var category_name = ""
    val requestToServer = RequestToServer

    lateinit var recordCategorySettingAdapter: RecordCategorySettingAdapter

    var datas = mutableListOf<RecordCategorySettingData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

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

        //LoadCategoryDatas()

        val bottomSheetDialogFragment = DialogFragment()


        val listener = object : CategorySetListener{
            override fun onSet(item: CategorySetInfo) {
                category_idx = item.categoryIdx
                category_name = item.name
                tv_category.text = category_name
            }
        }
        //카테고리 설정 클릭시
        tv_category.setOnClickListener{
//            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
//            setContentView(R.layout.layout_custom_category)
//            val bottom = BottomSheetDialog(this)
////            bottom.setContentView(R.layout.layout_custom_category)
//            bottom.show()

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
                --current_noti
                et_noti_count.setText((current_noti).toString())
            }
        }

        //발주 알림 개수 + 선택
        btn_noti_plus.setOnClickListener {
            ++current_noti
            et_noti_count.setText((current_noti).toString())
        }

        //발주 수량 개수 - 선택
        btn_order_minus.setOnClickListener {
            //0보다 작을수 없음
            if(current_order <= 0){
                current_order == 0
            }else{
                --current_order
                et_order_count.setText((current_order).toString())
            }
        }

        //발주 수량 개수 + 선택
        btn_order_plus.setOnClickListener {
            ++current_order
            et_order_count.setText((current_order).toString())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        icon_idx = data!!.getIntExtra("icon_idx", 0)
        icon_url = data!!.getStringExtra("icon_url")
        if(icon_url.isNullOrBlank()){
            icon_url = "null"
        }

        Glide.with(this).load(icon_url).into(btn_iconsetting)
    }

    private fun postRecordAddResponse(name: String, unit: String, alarmCnt: Int, orderCnt: Int){
        requestToServer.service.postRecordAddResponse(
            getString(R.string.test_token),
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
