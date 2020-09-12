package com.example.inventorybox.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.example.inventorybox.DB.SharedPreferenceController
import com.example.inventorybox.R
import com.example.inventorybox.data.PostItemInfo
import com.example.inventorybox.data.RequestPostExchangeItem
import com.example.inventorybox.data.ResponsePostExchangeItem
import com.example.inventorybox.etc.CustomDialog
import com.example.inventorybox.etc.PriceTextWatcher
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_exchange_post.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

class ExchangePostActivity : AppCompatActivity() {

    private val PICK_IMAGE = 1
    var isFood = true
    var hasExpireDate = true

    var map = HashMap<String, RequestBody>()
    lateinit var photoBody : RequestBody



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_post)

        setUserData()

        val dialog = CustomDialog(this)
        dialog.setTitle("그만두기")
        dialog.setContent("작성하시던 모든 내용이 사라집니다.")
        dialog.setNegativeBtn("취소") { v -> dialog.dismissDialog() }
        dialog.setPositiveBtn("나가기"
        ) {
            dialog.dismissDialog()
            finish()}

        // 뒤로가기 버튼 눌리면 alertdialog 보여준 후 나가기
        btn_finish.setOnClickListener {
            dialog.showDialog()
//            dialog.show()
//            finish()
        }



        // 완료 버튼 누르면,
        btn_exchange_post_confirm.setOnClickListener {
            val product_name = et_product_name.text.toString()
            val product_num = Integer.parseInt(et_product_num.text.toString())
            val product_unit = et_unit.text.toString()
            val product_price = Integer.parseInt(et_price_sell.text.toString().replace(",",""))
            val cover_price = Integer.parseInt(et_price_original.text.toString().replace(",",""))
            val description = et_description.text.toString()
            val expire_date : String? = if(hasExpireDate) "${et_expiredate_year.text.toString()}.${et_expiredate_month.text.toString()}.${et_expiredate_date.text.toString()}" else null

            val pic = uploadImage()
//
//            RequestToServer.service.postExchangeItem(
////                pic,
////                getString(R.string.test_token),
////                RequestPostExchangeItem(
////                    PostItemInfo(
////                        cover_price,
////                        description,
////                        expire_date,
////                        if(isFood)1 else 0,
////                        product_price,
////                        product_name,
////                        product_num,
////                        product_unit
////                    )
////                )
//            ).customEnqueue(
//                onSuccess = {
//                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
//                }
//            )
            val rq_cover_price = RequestBody.create(MediaType.parse("text/plain"), cover_price.toString())
            val rq_unit = RequestBody.create(MediaType.parse("text/plain"), product_unit.toString())
            val rq_price = RequestBody.create(MediaType.parse("text/plain"), product_price.toString())
            val rq_name = RequestBody.create(MediaType.parse("text/plain"), product_name.toString())
            val rq_quantity = RequestBody.create(MediaType.parse("text/plain"), product_num.toString())
            val rq_description = RequestBody.create(MediaType.parse("text/plain"), description)
            if(hasExpireDate){
                val rq_expireDate = RequestBody.create(MediaType.parse("text/plain"), expire_date)
                map.put("expDate", rq_expireDate)
            }
            val rq_food =  RequestBody.create(MediaType.parse("text/plain"), if(isFood) "1" else "0")
//
//
//            val rq_cover_price = RequestBody.create(MediaType.parse("text/plain"), "3000")
//            val rq_unit = RequestBody.create(MediaType.parse("text/plain"), "하")
//            val rq_price = RequestBody.create(MediaType.parse("text/plain"), "3000")
//            val rq_name = RequestBody.create(MediaType.parse("text/plain"), "콜라")
//            val rq_quantity = RequestBody.create(MediaType.parse("text/plain"), "3")
//            val rq_description = RequestBody.create(MediaType.parse("text/plain"), "맛있어요")
//            val rq_expireDate = RequestBody.create(MediaType.parse("text/plain"), "2020.2.1")
//            val rq_food =  RequestBody.create(MediaType.parse("text/plain"), "1")

//            map.put("productImg", photoBody)
            map.put("productName", rq_name)
            map.put("isFood", rq_food)
            map.put("price", rq_price)
            map.put("quantity", rq_quantity)
            map.put("description", rq_description)
            map.put("coverPrice", rq_cover_price)
            map.put("unit", rq_unit)

            RequestToServer.service.postExchangeItem(
                SharedPreferenceController.getUserToken(this),
                pic,
                map
            ).customEnqueue(
                onSuccess = {
                    finish()

                },
                onFail = {
                    Log.d("########","fail")
                },
                onError = {
                    Log.d("########","error")
                }
            )

//            finish()
        }
        btn_exchange_post_confirm.isEnabled = false
        // 모든 정보 입력했으면 버튼 활성화
//        val btn_activator = Btn_Activator(this, btn_exchange_post_confirm)
        val btn_listener = object : OnButtonListener{

            var isActivated = false
                override fun onCheck() {
                if( et_product_name.text.isNotEmpty()
                    && et_product_num.text.isNotEmpty()
                    && et_unit.text.isNotEmpty()
                    && et_price_sell.text.isNotEmpty()
                    && et_price_original.text.isNotEmpty()
                    && ((et_expiredate_date.text.isNotBlank() && et_expiredate_month.text.isNotBlank() && et_expiredate_year.text.isNotBlank())
                            || !hasExpireDate)
                    && et_description.text.isNotBlank()
                ){
                    btn_exchange_post_confirm.background = ContextCompat.getDrawable(applicationContext,R.drawable.rec30_yellow)
                    btn_exchange_post_confirm.isEnabled = true
                    isActivated=true
                }else if (isActivated){
                    btn_exchange_post_confirm.background = ContextCompat.getDrawable(applicationContext,R.drawable.graph_rec30_middlegrey)
                    btn_exchange_post_confirm.isEnabled=false
                    isActivated=false
                }
            }
        }


        // 사진 추가하기
        btn_add_img.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE)
        }

        // 식품 공산품 카테고리 설정하기
        btn_category_food.setOnClickListener {
            btn_category_food.setFocus(this, R.color.white)
            btn_category_not_food.removeFocus(this, R.color.darkgrey)
            isFood=true
        }
        btn_category_not_food.setOnClickListener {
            btn_category_not_food.setFocus(this, R.color.white)
            btn_category_food.removeFocus(this, R.color.darkgrey)
            isFood=false
        }

        // 제품 수량 등록 리스너
        val listener_num_product = View.OnClickListener{
            var change : Int
            if(it==btn_plus_num_product){
                change = 1
            }else{
                change = -1
            }
//            val value = if(et_product_num.text.toString().isNullOrEmpty()) 0 else Integer.parseInt(et_product_num.text.toString())

            val value = try{
                Integer.parseInt(et_product_num.text.toString())
            }catch (e : Exception){
                0
            }
            et_product_num.setText((value+change).toString())
            et_product_num.clearFocus()
            et_product_num.inputType = InputType.TYPE_CLASS_NUMBER
            btn_listener.onCheck()
        }
        //제품 수량 등록
        btn_minus_num_product.setOnClickListener(listener_num_product)
        btn_plus_num_product.setOnClickListener(listener_num_product)


        // 유통기한 버튼

        btn_expire_date.setOnClickListener {
            // 그전에 유통기한 있었으면
            if(hasExpireDate){
                btn_expire_date.setFocus(this, R.color.white)
                et_expiredate_year.clearSetHint("0000")
                et_expiredate_month.clearSetHint("00")
                et_expiredate_date.clearSetHint("00")
                et_expiredate_year.clearFocus()
                et_expiredate_month.clearFocus()
                et_expiredate_date.clearFocus()
                hasExpireDate = false
            }else{
                btn_expire_date.removeFocus(this, R.color.middlegrey)
                hasExpireDate=true
            }
            btn_listener.onCheck()
        }

        val expire_date_watcher = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val context: Context = applicationContext
                if(!hasExpireDate){
                    btn_expire_date.removeFocus(context, R.color.middlegrey)
                    hasExpireDate=true
                }
                btn_listener.onCheck()
            }

        }

        et_expiredate_date.addTextChangedListener(expire_date_watcher)
        et_expiredate_year.addTextChangedListener(expire_date_watcher)
        et_expiredate_month.addTextChangedListener(expire_date_watcher)

        // 가격 정보 입력하면 ,(comma) 추가
        et_price_original.addTextChangedListener(PriceTextWatcher(et_price_original))
        et_price_sell.addTextChangedListener(PriceTextWatcher(et_price_sell))


        et_description.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                btn_listener.onCheck()
            }

        })

    }

    private fun setUserData() {
        RequestToServer.service.requestExchangeUserInfo(
            SharedPreferenceController.getUserToken(this)
        ).customEnqueue(
            onSuccess = {
                tv_personal_name.text = it.data.userInfo.repName
                tv_personal_store.text = it.data.userInfo.coName
                tv_personal_loca.text = it.data.userInfo.location
                tv_personal_phone.text = it.data.userInfo.phoneNumber
            }
        )
    }

    var selectedPicUri : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE){
            data?.let{
                selectedPicUri = it.data!!
                Glide.with(this).load(selectedPicUri).into(btn_add_img)

            }
            try{
//                uploadImage()

            }catch (e : IOException){
                e.printStackTrace()
            }
        }
    }

    // 카테고리 버튼 포커스 변경하기
    fun Button.setFocus(context: Context, colorId: Int){
        this.background = ContextCompat.getDrawable(context, R.drawable.graph_rec9_yellow)
        this.setTextColor(context.getColor(colorId))
    }
    fun Button.removeFocus(context: Context, colorId : Int){
        this.background = ContextCompat.getDrawable(context, R.drawable.graph_rec9_middlegrey_blank)
        this.setTextColor(context.getColor(colorId))
    }
    fun EditText.clearSetHint(text : String){
        this.text.clear()
        this.hint = text
    }

    // 이미지 파일 서버로 내보내기
    fun uploadImage() : MultipartBody.Part{
        val options = BitmapFactory.Options()
        val inputStream: InputStream = contentResolver.openInputStream(selectedPicUri!!)!!
        val bitmap = BitmapFactory.decodeStream(inputStream,null,options)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream)
        photoBody = RequestBody.create(MediaType.parse("image/jpeg"),byteArrayOutputStream.toByteArray())
        val picture_rb = MultipartBody.Part.createFormData("productImg", File(selectedPicUri.toString()).name,photoBody)
        Log.d("exchangepostactivity",picture_rb.toString())

        return picture_rb

//        val reqBody : RequestBody = RequestBody.create(MediaType.parse("image/jpeg", photoBod))
    }
    interface OnButtonListener{
        fun onCheck(){}
    }

}