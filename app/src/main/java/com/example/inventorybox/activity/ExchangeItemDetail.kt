package com.example.inventorybox.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.inventorybox.DB.SharedPreferenceController
import com.example.inventorybox.ExchangeModifyActivity
import com.example.inventorybox.R
import com.example.inventorybox.data.RequestExchangeLikeStatus
import com.example.inventorybox.etc.CustomDialog
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_exchange_item_detail.*
import kotlinx.android.synthetic.main.layout_custom_toast.view.*
import java.security.Permission
import java.util.jar.Manifest

class ExchangeItemDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_item_detail)

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
            SharedPreferenceController.getUserToken(this),
            postIdx
        ).customEnqueue(
            onSuccess = {
                tv_item_name.text = it.data.itemInfo.productName
                tv_item_category.text = if(it.data.itemInfo.isFood==1)"식품" else "공산품"
                tv_item_distance.text = computeDistance(it.data.itemInfo.distDiff)
                tv_item_post_date.text = it.data.itemInfo.uploadDate
                val price = it.data.itemInfo.price.toString() + " 원"
                tv_exchange_item_price.text = price
                val cover_price = it.data.itemInfo.coverPrice.toString() + "원"
                tv_exchange_item_cover_price.text = cover_price
                val quantity = it.data.itemInfo.quantity.toString() + it.data.itemInfo.unit
                tv_exchange_item_num.text = quantity
                tv_exchange_expire_date.text = if(it.data.itemInfo.expDate.isNullOrEmpty()||it.data.itemInfo.expDate=="undefined")"없음" else it.data.itemInfo.expDate
                tv_item_description.text = it.data.itemInfo.description
                Glide.with(this).load(it.data.itemInfo.productImg).into(iv_exchange_img)

                //사용자 이름
                tv_personal_name.text = it.data.userInfo.repName
                tv_personal_store.text = it.data.userInfo.coName
                tv_personal_loca.text = it.data.userInfo.location
                tv_personal_phone.text = it.data.userInfo.phoneNumber

                val idx = it.data.itemInfo.postIdx
                // 내가 작성자면 (userCheck==1)
                if(it.data.itemInfo.userCheck==1){
                    tv_exchange_detail_title.visibility = View.VISIBLE
                    btn_edit.text = "수정하기"
                    btn_exchange_detail_call.text = "거래완료"
                    btn_exchange_detail_call.setOnClickListener {
                        val dialog = CustomDialog(this)
                        dialog.setTitle("거래완료를 완료하시겠습니까?")
                        dialog.setContent("작성하신 게시글은 영구 삭제됩니다.")
                        dialog.setNegativeBtn("취소") {dialog.dismissDialog()}
                        dialog.setPositiveBtn("확인"
                        ) {
                            changeSoldStatus(idx)
                            deletePost(idx)
                            dialog.dismissDialog()
                        }
                        dialog.showDialog()
                    }
                    btn_edit.setOnClickListener {
                        val intent = Intent(this, ExchangeModifyActivity::class.java)
                        intent.putExtra("post_idx", idx)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }

                }else{
                    tv_exchange_detail_title.visibility = View.INVISIBLE
                    btn_edit.text = "좋아요"
                    btn_exchange_detail_call.text = "전화하기"
                    btn_edit.setOnClickListener {
                        setHeart(idx)
                    }



                    val phone_num = it.data.userInfo.phoneNumber
                    btn_exchange_detail_call.setOnClickListener {

                        val dialog = CustomDialog(this)
                        dialog.setTitle("전화하기")
                        dialog.setContent("재고 교환을 위해 작성자에게 전화를 겁니다.\n" +
                                "전화하시려면 통화버튼을 눌러주세요.")
                        dialog.setNegativeBtn("취소") { v -> dialog.dismissDialog() }
                        dialog.setPositiveBtn("통화하기"
                        ) {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone_num))
//                        intent.setData(Uri.parse("tel:"+phone_num))
                            try{
                                startActivity(intent)
                            }catch (e : Exception){
                                Log.d("ExchangeItemDetail",e.toString())
                            }
                        }
                        dialog.showDialog()


                    }
                }
            }
        )
    }



    fun computeDistance(dist : Int) : String{
        if(dist<1000){
            return dist.toString()+"m"
        }else{
            return "%.1fkm".format(dist.toDouble()/1000)
        }
    }
    fun setHeart(idx : Int){
        RequestToServer.service.requestExchangeLikeStatus(
            SharedPreferenceController.getUserToken(this),
            RequestExchangeLikeStatus(
               idx
            )
        ).customEnqueue(
            onSuccess = {
                if(it.data.likes==1){
                    showToast(this, "좋아요")
                }else{
                    showToast(this, "좋아요 취소")

                }
            }
        )
    }
    fun changeSoldStatus(idx : Int){
        RequestToServer.service.requestExchangeSoldStatus(
            SharedPreferenceController.getUserToken(this),
            RequestExchangeLikeStatus(
                idx
            )
        ).customEnqueue(
            onSuccess = {}
        )
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
    fun deletePost(idx : Int){
        RequestToServer.service.requestExchangePostDelete(
            SharedPreferenceController.getUserToken(this),
            idx
        ).customEnqueue(
            onSuccess = {
                finish()
            }
        )
    }

}