package com.example.inventorybox

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.example.inventorybox.activity.SignUpTerms
import com.example.inventorybox.data.RequestNicknameCheck
import com.example.inventorybox.network.ApplicationController
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_add.view.*
import kotlinx.android.synthetic.main.activity_exchange_post.*
import kotlinx.android.synthetic.main.activity_sign_up_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.Exception

class SignUpProfile : AppCompatActivity() {

    val PICK_IMAGE = 0
    var selectedPicUri : Uri? = null
    var isPictureSelected =false
    var isNicknameFilled = false


    var map = HashMap<String, RequestBody>()

    lateinit var photoBody : RequestBody


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_profile)


        btn_back_signup_profile.setOnClickListener {
            finish()
        }

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
//            // error message
//            et_signup_profile_nickname.background = getDrawable(R.drawable.signup_profile_et_background_error)
//            tv_signup_profile_error_msg.visibility = View.VISIBLE

            val nickname = et_signup_profile_nickname.text.toString()
            val global = ApplicationController
            global.img = uploadImage()
            global.nickname = nickname
            RequestToServer.service.requestNicknameCheck(
                RequestNicknameCheck(
                    nickname
                )
            ).customEnqueue(
                onSuccess = {
                    if(it.data.result){
                        tv_signup_profile_error_msg.visibility = View.INVISIBLE
                        et_signup_profile_nickname.background = getDrawable(R.drawable.signup_profile_et_backgrond)
//
//
//
//                        map.put("nickname", getRq(nickname))
//                        map.put("email", getRq("bkje@ldkjdgf"))
//                        map.put("password", getRq("a"))
//                        map.put("repName", getRq(global.rep_name))
//                        map.put("coName",getRq(global.co_name))
//                        map.put("phoneNumber",getRq(global.phone_num))
//                        RequestToServer.service.requestSignUp(
//                            uploadImage(),
//                            map
//                        ).customEnqueue(
//                            onSuccess = {
//                                Log.d("signup profile", "success")
//                                Log.d("signup profile", "${global.email}      ${global.img}")
//
//                                val intent = Intent(this, SignUpTerms::class.java)
//                                startActivity(intent)
//                            }
//                        )

                        val intent = Intent(this, SignUpTerms::class.java)
                        startActivity(intent)
                    }else{
                        tv_signup_profile_error_msg.visibility = View.VISIBLE
                        et_signup_profile_nickname.background = getDrawable(R.drawable.signup_profile_et_background_error)
                    }

                }
            )

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


    fun uploadImage() : MultipartBody.Part{
        val options = BitmapFactory.Options()
        val inputStream: InputStream = contentResolver.openInputStream(selectedPicUri!!)!!
        val bitmap = BitmapFactory.decodeStream(inputStream,null,options)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream)
        photoBody = RequestBody.create(MediaType.parse("image/jpeg"),byteArrayOutputStream.toByteArray())
        val picture_rb = MultipartBody.Part.createFormData("img", File(selectedPicUri.toString()).name,photoBody)
        Log.d("signupprofile activity",picture_rb.toString())

        return picture_rb

//        val reqBody : RequestBody = RequestBody.create(MediaType.parse("image/jpeg", photoBod))
    }

    fun getRq(s : String): RequestBody{
        return RequestBody.create(MediaType.parse("text/plain"), s)
    }
    fun dataFilledListener(){
        if(isPictureSelected&&isNicknameFilled){
            btn_signup_profile_confirm.isEnabled = true
            btn_signup_profile_confirm.background = getDrawable(R.drawable.rec30_yellow_gradient)
        }
    }
}