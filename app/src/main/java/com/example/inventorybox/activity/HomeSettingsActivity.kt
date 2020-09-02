package com.example.inventorybox.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.etc.CustomDialog
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_home_settings.*


class HomeSettingsActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_settings)

        //뒤로가기 이미지 클릭 시
       img_home_setting_back.setOnClickListener {
            finish()
        }

        val logout_dialog = CustomDialog(this)
        logout_dialog.setTitle("로그아웃")
        logout_dialog.setContent("기록된 정보들은 다시 로그인을 하여 확인하실 수 있습니다.")
        logout_dialog.setNegativeBtn("취소") { v -> logout_dialog.dismissDialog() }
        logout_dialog.setPositiveBtn("로그아웃"
        ) {
            logout_dialog.dismissDialog()
            finish()
            //MainActivity로 전달
            val intent = Intent("finish_activity")
            sendBroadcast(intent)
        }

        //로그아웃 클릭 시 다이얼로그
        tv_home_setting_logout.setOnClickListener{
            logout_dialog.showDialog()
        }

        val withdraw_dialog = CustomDialog(this)
        withdraw_dialog.setTitle("회원탈퇴")
        withdraw_dialog.setContent("계정에 저장된 개인정보가 모두 삭제됩니다. 탈퇴 후 삭제된 정보는 다시 복구되지 않습니다.")
        withdraw_dialog.setNegativeBtn("취소") { v -> withdraw_dialog.dismissDialog() }
        withdraw_dialog.setPositiveBtn("탈퇴"
        ) {
            withdraw_dialog.dismissDialog()
            userDelete()
            finish()}

        //회원탈퇴 클릭 시 다이얼로그
        tv_home_setting_withdraw.setOnClickListener{
            withdraw_dialog.showDialog()
        }
    }

    private fun userDelete(){
        requestToServer.service.deleteUser(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                Log.d("delete_user", "회원 탈퇴 성공")
            }
        )
    }

}