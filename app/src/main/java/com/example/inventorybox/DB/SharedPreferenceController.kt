package com.example.inventorybox.DB

import android.content.Context
import android.content.SharedPreferences
import android.service.autofill.UserData
import com.example.inventorybox.network.POST.ResponseLogin
import com.example.inventorybox.network.POST.SomeData

object SharedPreferenceController {
    val MY_ACCOUNT = "unique_string"

    //유저 정보 저장
    fun setUserInfo(ctx: Context, userData: SomeData) {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("u_token", userData.jwt)
        editor.apply()
    }

    fun getUserToken(ctx: Context): String? {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("u_token", "")!!
    }
}