package com.inventorybox.inventorybox.etc

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue


fun Context.dpToFloat(dipValue: Float): Float {
    val metrics: DisplayMetrics = this.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
}