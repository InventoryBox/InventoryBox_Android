package com.inventorybox.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.OnBoarding
import com.inventorybox.inventorybox.R

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
        if (SharedPreferenceController.getUserEmail(this)!!.isNullOrEmpty()){
            startActivity(Intent(this@SplashActivity, OnBoarding::class.java))
            finish()
        }else{
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }
    }
}

