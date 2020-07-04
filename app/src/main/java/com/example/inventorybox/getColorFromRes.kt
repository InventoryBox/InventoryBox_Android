package com.example.inventorybox

import android.content.Context
import androidx.core.content.ContextCompat

// color res id로부터 color 값 반환
fun Context.getColorFromRes(color:Int):Int{
    return ContextCompat.getColor(this, color)
}