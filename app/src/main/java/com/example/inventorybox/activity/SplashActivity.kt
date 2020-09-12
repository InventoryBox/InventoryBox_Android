package com.example.inventorybox.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.inventorybox.DB.SharedPreferenceController
import com.example.inventorybox.OnBoarding
import com.example.inventorybox.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

    }

    override fun onStart() {
        super.onStart()

        val mHandler = Handler()
        mHandler.postDelayed(startConfirmUser, 2000)
    }


    //이메일을 받아와서 아이디가 있는지 확인하고 있으면 메인홈으로 없으면 로그인화면으로
    internal val startConfirmUser: Runnable = Runnable {
        if (SharedPreferenceController.getUserEmail(this)!!.isEmpty()){
            startActivity(Intent(this@SplashActivity, OnBoarding::class.java))
            finish()
        }else{
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }
    }
}

