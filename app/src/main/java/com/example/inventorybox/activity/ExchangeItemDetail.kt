package com.example.inventorybox.activity

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.etc.CustomDialog
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_exchange_item_detail.*

class ExchangeItemDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_item_detail)

        val post_idx = intent.getIntExtra("post_idx",0)
//        getPostData(post_idx)


//
//        // 상세 설명 페이지 밑줄
//        //test
//        val s = getString(R.string.test)
//        val span_s = SpannableString(s)
//        span_s.setSpan(UnderlineSpan(),0, span_s.length, 0)
//        tv_item_description.setText(span_s)

        // 나가기 버튼
        btn_finish.setOnClickListener {
            finish()
        }

        btn_edit.setOnClickListener {
//            val view = LayoutInflater.from(this).inflate(R.layout.layout_custom_dialog, null)
//            val builder = AlertDialog.Builder(this)
//                .setView(view)
//
//            val dialog = builder.show()



        }
    }

    override fun onStart() {
        super.onStart()
        val post_idx = intent.getIntExtra("post_idx",0)
        getPostData(post_idx)
    }

    private fun getPostData(postIdx: Int) {
        RequestToServer.service.requestExchangeItemDetail(
            getString(R.string.test_token),
            postIdx
        ).customEnqueue(
            onSuccess = {
                tv_item_name.text = it.data.itemInfo.productName
                tv_item_category.text = if(it.data.itemInfo.isFood==1)"식품" else "공산품"
//                tv_item_distance.text = it.data.itemInfo.
                tv_item_post_date.text = it.data.itemInfo.uploadDate
                val price = it.data.itemInfo.price.toString() + " 원"
                tv_exchange_item_price.text = price
                val cover_price = it.data.itemInfo.coverPrice.toString() + "원"
                tv_exchange_item_cover_price.text = cover_price
                val quantity = it.data.itemInfo.quantity.toString() + it.data.itemInfo.unit
                tv_exchange_item_num.text = quantity
                tv_exchange_expire_date.text = it.data.itemInfo.expDate
                tv_item_description.text = it.data.itemInfo.description
                Glide.with(this).load(it.data.itemInfo.productImg).into(iv_exchange_img)

                //사용자 이름
                tv_personal_name.text = it.data.userInfo.repName
                tv_personal_store.text = it.data.userInfo.coName
                tv_personal_loca.text = it.data.userInfo.location
                tv_personal_phone.text = it.data.userInfo.phoneNumber

            }
        )
    }
}