package com.example.inventorybox.activity

import android.content.Context
import android.content.Intent
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
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.etc.CustomDialog
import com.example.inventorybox.etc.PriceTextWatcher
import kotlinx.android.synthetic.main.activity_exchange_post.*
import java.io.IOException
import java.lang.Exception
import java.text.NumberFormat
import java.util.*

class ExchangePostActivity : AppCompatActivity() {

    private val PICK_IMAGE = 1
    var isFood = true
    var hasExpireDate = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_post)

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



        btn_exchange_post_confirm.setOnClickListener {
            finish()
        }
        btn_exchange_post_confirm.isEnabled = false
        // 모든 정보 입력했으면 버튼 활성화
//        val btn_activator = Btn_Activator(this, btn_exchange_post_confirm)
        val btn_listener = object : OnButtonListener{

            var isActivated = false
                override fun onCheck() {
                if( et_product_name.text.isNotBlank()
                    && et_product_num.text.isNotBlank()
                    && et_unit.text.isNotBlank()
                    && et_price_sell.text.isNotBlank()
                    && et_price_original.text.isNotBlank()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE){
            val image_path = data?.data
            try{
                Glide.with(this).load(image_path).into(btn_add_img)
                Log.d("test3", image_path.toString())

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

    interface OnButtonListener{
        fun onCheck(){}
    }

}