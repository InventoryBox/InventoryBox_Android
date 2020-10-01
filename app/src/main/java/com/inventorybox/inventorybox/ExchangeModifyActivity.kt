package com.inventorybox.inventorybox

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
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.activity.ExchangePostActivity
import com.inventorybox.inventorybox.etc.CustomDialog
import com.inventorybox.inventorybox.etc.PriceTextWatcher
import com.inventorybox.inventorybox.etc.showCustomToast
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_exchange_modify.*
import kotlinx.android.synthetic.main.activity_exchange_post.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.Exception

class ExchangeModifyActivity : AppCompatActivity() {

    var hasExpireDate = false
    var selectedPicUri : Uri? = null
    private val PICK_IMAGE = 1
    var isFood = true

    var img_url: String =""
    var idx : Int = -1

    var map = HashMap<String, RequestBody>()
    lateinit var photoBody : RequestBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_modify)

        // 유저 정보등록
        setUserData()

        // 수정 게시글 정보 조회
        getData()


        // 게시글 삭제하기
        btn_exchange_modify_delete.setOnClickListener {
            val dialog = CustomDialog(this)
            dialog.setTitle("삭제하시겠습니까?")
            dialog.setContent("작성하신 게시글은 영구 삭제됩니다.")
            dialog.setNegativeBtn("아니오") {dialog.dismissDialog()}
            dialog.setPositiveBtn("예"
            ) {
                deletePost(idx)
//                baseContext.showCustomToast("삭제되었습니다")
                dialog.dismissDialog()
//                finish()
            }
            dialog.showDialog()
        }
        btn_exchange_modify_finish.setOnClickListener {
            finish()
        }

        // 사진 추가하기
       btn_modify_add_img.setOnClickListener {
           val intent = Intent(Intent.ACTION_PICK)
           intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
           startActivityForResult(intent, PICK_IMAGE)
       }
        // 식품 공산품
        btn_modify_category_food.setOnClickListener {
            btn_modify_category_food.setFocus(this, R.color.white)
            btn_modify_category_not_food.removeFocus(this, R.color.darkgrey)
            isFood=true
        }
        btn_modify_category_not_food.setOnClickListener {
            btn_modify_category_not_food.setFocus(this, R.color.white)
            btn_modify_category_food.removeFocus(this, R.color.darkgrey)
            isFood=false
        }

        // 제품 수량 리스너

        val listener_num_product = View.OnClickListener{
            var change : Int
            if(it==btn_modify_plus){
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
            et_modify_product_num.setText((value+change).toString())
            et_modify_product_num.clearFocus()
            et_modify_product_num.inputType = InputType.TYPE_CLASS_NUMBER
            btn_listener.onCheck()
        }
        //제품 수량 등록
        btn_modify_plus.setOnClickListener(listener_num_product)
        btn_modify_minus.setOnClickListener(listener_num_product)

        btn_modify_expire_date.setOnClickListener {
            // 그전에 유통기한 있었으면
            if(hasExpireDate){
                btn_modify_expire_date.setFocus(this, R.color.white)
                et_modify_expiredate_year.clearSetHint("0000")
                et_modify_expiredate_month.clearSetHint("00")
                et_modify_expiredate_date.clearSetHint("00")
                et_modify_expiredate_year.clearFocus()
                et_modify_expiredate_month.clearFocus()
                et_modify_expiredate_date.clearFocus()
                hasExpireDate = false
            }else{
                btn_modify_expire_date.removeFocus(this, R.color.middlegrey)
                hasExpireDate=true
            }
            btn_listener.onCheck()
        }

        val expire_date_watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val context: Context = applicationContext
                if(!hasExpireDate){
                    btn_modify_expire_date.removeFocus(context, R.color.middlegrey)
                    hasExpireDate=true
                }
                btn_listener.onCheck()
            }

        }

        et_modify_expiredate_date.addTextChangedListener(expire_date_watcher)
        et_modify_expiredate_year.addTextChangedListener(expire_date_watcher)
        et_modify_expiredate_month.addTextChangedListener(expire_date_watcher)

        // 가격 정보 입력하면 ,(comma) 추가
        et_modify_price_original.addTextChangedListener(PriceTextWatcher(et_modify_price_original))
        et_modify_price_sell.addTextChangedListener(PriceTextWatcher(et_modify_price_sell))


        et_modify_description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                btn_listener.onCheck()
            }

        })

        // 완료버튼
        btn_exchange_modify_confirm.setOnClickListener {
            val product_name = et_modify_product_name.text.toString()
            val product_num = Integer.parseInt(et_modify_product_num.text.toString())
            val product_unit = et_modify_product_unit.text.toString()
            val product_price = Integer.parseInt(et_modify_price_sell.text.toString().replace(",",""))
            val cover_price = Integer.parseInt(et_modify_price_original.text.toString().replace(",",""))
            val description = et_modify_description.text.toString()
            val expire_date : String? = if(hasExpireDate) "${et_modify_expiredate_year.text.toString()}.${et_modify_expiredate_month.text.toString()}.${et_modify_expiredate_date.text.toString()}" else null

            photoBody = RequestBody.create(MediaType.parse("image/jpeg"),img_url.toByteArray())
            val pic = if(selectedPicUri==null){
                null
            }else{
                uploadImage()
            }
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
            val rq_idx = RequestBody.create(MediaType.parse("text/plain"), idx.toString())
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
            map.put("postIdx",rq_idx)

            RequestToServer.service.postExchangeModify(
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
        }

    }

    private fun getData() {
        idx = intent.getIntExtra("post_idx", -1)
        RequestToServer.service.requestExchangeItemDetail(
            SharedPreferenceController.getUserToken(this),
            idx
        ).customEnqueue(
            onSuccess = {
                img_url = it.data.itemInfo.productImg
                Glide.with(this).load(img_url).into(btn_modify_add_img)
                et_modify_product_name.setText(it.data.itemInfo.productName)
                et_modify_product_num.setText(it.data.itemInfo.quantity.toString())
                et_modify_product_unit.setText(it.data.itemInfo.unit)
                if(it.data.itemInfo.isFood==1){
                    btn_modify_category_food.setFocus(this, R.color.white)
                    btn_modify_category_not_food.removeFocus(this, R.color.darkgrey)
                }else{
                    btn_modify_category_not_food.setFocus(this, R.color.white)
                    btn_modify_category_food.removeFocus(this, R.color.darkgrey)
                }

                et_modify_price_sell.setText(it.data.itemInfo.price.toString())
                et_modify_price_original.setText(it.data.itemInfo.coverPrice.toString())

                if(it.data.itemInfo.expDate==null||it.data.itemInfo.expDate=="undefined"){
                    btn_modify_expire_date.setFocus(this, R.color.white)
                    hasExpireDate=false
                }else{
                    try{
                        val array = it.data.itemInfo.expDate.split('.')
                        btn_modify_expire_date.removeFocus(this, R.color.middlegrey)
                        et_modify_expiredate_year.setText(array[0])
                        et_modify_expiredate_month.setText(array[1])
                        et_modify_expiredate_date.setText(array[2])
                        hasExpireDate=true
                    }catch (e:Exception){

                    }
                }
                et_modify_description.setText(it.data.itemInfo.description)

            }
        )
    }

    private fun setUserData() {
        RequestToServer.service.requestExchangeUserInfo(
            SharedPreferenceController.getUserToken(this)
        ).customEnqueue(
            onSuccess = {
                tv_modify_name.text = it.data.userInfo.repName
                tv_modify_personal_store.text = it.data.userInfo.coName
                tv_modify_personal_loca.text = it.data.userInfo.location
                tv_modify_personal_phone.text = it.data.userInfo.phoneNumber
            }
        )
    }

    fun deletePost(idx : Int){

        Log.e("exchange modify activity",idx.toString())
        RequestToServer.service.requestExchangePostDelete(
            SharedPreferenceController.getUserToken(this),
            idx
        ).customEnqueue(
            onSuccess = {
                baseContext.showCustomToast("삭제되었습니다")
                finish()
            }
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE){
            data?.let{
                selectedPicUri = it.data!!
                Glide.with(this).load(selectedPicUri).into(btn_modify_add_img)

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

    val btn_listener = object : ExchangePostActivity.OnButtonListener {

        var isActivated = false
        override fun onCheck() {
            if( et_modify_product_name.text.isNotEmpty()
                && et_modify_product_num.text.isNotEmpty()
                && et_modify_product_unit.text.isNotEmpty()
                && et_modify_price_sell.text.isNotEmpty()
                && et_modify_price_original.text.isNotEmpty()
                && ((et_modify_expiredate_year.text.isNotBlank() && et_modify_expiredate_month.text.isNotBlank() && et_modify_expiredate_year.text.isNotBlank())
                        || !hasExpireDate)
                && et_modify_description.text.isNotBlank()
            ){
                btn_exchange_modify_confirm.isEnabled = true
                isActivated=true
            }else if (isActivated){
                btn_exchange_modify_confirm.isEnabled=false
                isActivated=false
            }
        }
    }
}