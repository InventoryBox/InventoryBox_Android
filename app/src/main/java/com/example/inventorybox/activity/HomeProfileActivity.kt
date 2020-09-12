package com.example.inventorybox.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.inventorybox.DB.SharedPreferenceController
import com.example.inventorybox.R
import com.example.inventorybox.data.RequestNicknameCheck
import com.example.inventorybox.data.RequestProfile
import com.example.inventorybox.data.RequestSignup
import com.example.inventorybox.network.ApplicationController
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.acitivity_home_profile.*
import kotlinx.android.synthetic.main.activity_exchange_item_detail.*
import kotlinx.android.synthetic.main.activity_exchange_post.*
import kotlinx.android.synthetic.main.activity_sign_up_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

class HomeProfileActivity : AppCompatActivity() {

    private val PICK_IMAGE = 1

    val requestToServer = RequestToServer

    lateinit var photoBody : RequestBody

    var img_url: String =""

    // multipart form으로 보내기 위해
    var map = HashMap<String, RequestBody>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_home_profile)

        //프로필 조회
        profileResponse()

        btn_back_home_profile.setOnClickListener {
            finish()
        }

        //사진 변경하기
        frameLayout.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, PICK_IMAGE)
        }

        //포커스 되면 변화
        et_profile_nickname.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.rec9_grey_yellow_border)
                    btn_profile.setBackgroundResource(R.drawable.rec30_yellow)
                }
                else {
                    v.setBackgroundResource(R.drawable.rec9_grey)
                    btn_profile.setBackgroundResource(R.drawable.rec30_grey)
                }
            }


        btn_profile.setOnClickListener {
            nicknameCheck()
        }

    }

    var selectedPicUri : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        btn_profile.setBackgroundResource(R.drawable.rec30_yellow)

        if(requestCode==PICK_IMAGE){
            data?.let{
                selectedPicUri = it.data!!
                Glide.with(this).load(selectedPicUri).into(iv_home_profile)

            }
            try{
//                uploadImage()

            }catch (e : IOException){
                e.printStackTrace()
            }
        }
    }

    private fun profileResponse() {
        requestToServer.service.getProfile(
            SharedPreferenceController.getUserToken(this)
        ).customEnqueue(
            onSuccess = {
                Log.d("profile", "프로필 조회 성공")

                Glide.with(this).load(it.data.img).into(iv_home_profile)
                et_profile_nickname.setText(it.data.nickname.toString())
            }
        )
    }

    private fun sendProfile() {
        val changed_nickname = et_profile_nickname.text.toString()
        val rq_nickname = RequestBody.create(MediaType.parse("text/plain"), changed_nickname.toString())

        val pic = if(selectedPicUri==null){
            null
        }else{
            uploadImage()
        }

        map.put("nickName", rq_nickname)


        requestToServer.service.requestProfile2(
            SharedPreferenceController.getUserToken(this),
            pic,
            map
        ).customEnqueue(
            onFail = {
                Log.d("profile request", "프로필 변경 실패")
            },
            onSuccess = {
                Log.d("profile request", "프로필 변경 성공")
                Log.d("profile request", "${changed_nickname}")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
            onError = {
                Log.d("profile request", "에러")
            }
        )
    }

    private fun nicknameCheck() {
        val nickname = et_profile_nickname.text.toString()

        RequestToServer.service.requestNicknameCheck(
            RequestNicknameCheck(
                nickname
            )
        ).customEnqueue(
            onSuccess = {
                if(it.data.result){
                    tv_profile_error_msg.visibility = View.INVISIBLE
                    et_profile_nickname.background = getDrawable(R.drawable.signup_profile_et_backgrond)

                    sendProfile()

                }else{
                    tv_profile_error_msg.visibility = View.VISIBLE
                    et_profile_nickname.background = getDrawable(R.drawable.signup_profile_et_background_error)
                }

            }
        )
    }



    // 이미지 파일 서버로 내보내기
    fun uploadImage() : MultipartBody.Part{
        val options = BitmapFactory.Options()
        val inputStream: InputStream = contentResolver.openInputStream(selectedPicUri!!)!!
        val bitmap = BitmapFactory.decodeStream(inputStream,null,options)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream)
        photoBody = RequestBody.create(MediaType.parse("image/jpeg"),byteArrayOutputStream.toByteArray())
        val picture_rb = MultipartBody.Part.createFormData("img", File(selectedPicUri.toString()).name,photoBody)
        Log.d("profilephoto",picture_rb.toString())

        return picture_rb

//        val reqBody : RequestBody = RequestBody.create(MediaType.parse("image/jpeg", photoBod))
    }

}