package com.inventorybox.inventorybox.DB

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceController {
    val MY_ACCOUNT = "unique_string"

    //유저 정보 저장
    fun setUserInfo(ctx: Context, value: String) {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        //editor.putString("u_email", value)
        editor.putString("u_token", value)
        editor.commit()
    }

    fun setUserEmail(ctx: Context, value: String) {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("u_email", value)
        editor.commit()
    }

    fun setUserPW(ctx: Context, value: String) {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("u_password", value)
        editor.commit()
    }

    //유저 정보 보내주기
    fun getUserEmail(ctx: Context): String? {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("u_email", "")
    }
    fun getUserToken(ctx: Context): String {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("u_token","")!!
    }
    fun getUserPW(ctx: Context): String? {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return preference.getString("u_password","")
    }


    //유저 정보 해제
    fun clearUserToken(ctx: Context) {
        val preference: SharedPreferences = ctx.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("u_email", " ")
        editor.apply()
//        editor.clear()
//        editor.apply()
    }

}