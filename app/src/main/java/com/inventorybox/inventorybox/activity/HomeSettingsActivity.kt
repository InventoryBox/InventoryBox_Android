package com.inventorybox.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.etc.CustomDialog
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue
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

        //서비스 정보 클릭 시
        cl_setting_service.setOnClickListener {
            val intent = Intent(this, ServiceActivity::class.java)
            startActivity(intent)
        }

        //재고창고 사용법 클릭 시
        cl_setting_instruction.setOnClickListener {
            val intent = Intent(this, SettingInstructionsActivity::class.java)
            startActivity(intent)
        }

        val logout_dialog = CustomDialog(this)
        logout_dialog.setTitle("로그아웃")
        logout_dialog.setContent("기록된 정보들은 다시 로그인을 하여\n 확인하실 수 있습니다.")
        logout_dialog.setNegativeBtn("취소") { v -> logout_dialog.dismissDialog() }
        logout_dialog.setPositiveBtn("로그아웃"
        ) {
            logout_dialog.dismissDialog()
            SharedPreferenceController.clearUserToken(applicationContext)
            finish()
            //MainActivity로 전달 - MainActivity 끝내기
            val intent = Intent("finish_activity")
            sendBroadcast(intent)
        }

        //로그아웃 클릭 시 다이얼로그
        cl_setting_logout.setOnClickListener{
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
            //MainActivity로 전달 - MainActivity 끝내기
            val intent = Intent("finish_activity")
            sendBroadcast(intent)
        }

        //회원탈퇴 클릭 시 다이얼로그
        tv_home_setting_withdraw.setOnClickListener{
            withdraw_dialog.showDialog()
        }
    }

    private fun userDelete(){
        requestToServer.service.deleteUser(
            SharedPreferenceController.getUserToken(this)
        ).customEnqueue(
            onSuccess = {
                Log.d("delete_user", "회원 탈퇴 성공")
            }
        )
    }

}