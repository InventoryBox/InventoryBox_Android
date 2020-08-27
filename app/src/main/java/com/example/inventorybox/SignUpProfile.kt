package com.example.inventorybox

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add.view.*
import kotlinx.android.synthetic.main.activity_exchange_post.*
import kotlinx.android.synthetic.main.activity_sign_up_profile.*
import java.io.IOException

class SignUpProfile : AppCompatActivity() {

    val PICK_IMAGE = 0
    var selectedPicUri : Uri? = null
    var isPictureSelected =false
    var isNicknameFilled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_profile)

        // 프로필 누르면 갤러리에서 사진 가져오기
        img_signup_profile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE)
        }

        et_signup_profile_nickname.addTextChangedListener(
            object : TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                    if(p0!=null){
                        isNicknameFilled = true
                        dataFilledListener()
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            }
        )
        // 확인 버튼 누르면
        btn_signup_profile_confirm.setOnClickListener {
            et_signup_profile_nickname.background = getDrawable(R.drawable.signup_profile_et_background_error)
            tv_signup_profile_error_msg.visibility = View.VISIBLE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE && resultCode== Activity.RESULT_OK){
            signup_profile_default.visibility = View.INVISIBLE
            data?.let{
                selectedPicUri = it.data!!
                Glide.with(this).load(selectedPicUri).into(img_signup_profile)
                isPictureSelected = true
                dataFilledListener()
            }

        }
    }

    fun dataFilledListener(){
        if(isPictureSelected&&isNicknameFilled){
            btn_signup_profile_confirm.isEnabled = true
            btn_signup_profile_confirm.background = getDrawable(R.drawable.rec30_yellow_gradient)
        }
    }
}