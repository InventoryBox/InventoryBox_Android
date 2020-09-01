package com.example.inventorybox.etc

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.layout_custom_dialog.*
import kotlinx.android.synthetic.main.layout_custom_dialog.view.*

class CustomDialog(context: Context) : Dialog(context) {


    val view = LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, null)
    val builder = androidx.appcompat.app.AlertDialog.Builder(context)
        .setView(view)
    val dialog = builder.create()

    init {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    fun setPositiveBtn(s : String){
        view.btn_positive.text = s
    }

    fun setNegativeBtn(s: String, listener : (v : View)-> Unit){
        view.btn_positive.text = s
        view.btn_positive.setOnClickListener(
            listener
        )
    }
    fun setNegativeBtn(s: String){
        view.btn_negative.text = s
    }

    fun setPositiveBtn(s: String, listener : (v : View)-> Unit){
        view.btn_negative.text = s
        view.btn_negative.setOnClickListener(
            listener
        )
    }

    fun setTitle(s: String){
        view.tv_dialog_title.text = s
    }

    fun setContent(s : String){
        view.tv_dialog_content.text = s
    }
    fun showDialog(){
        dialog.show()
    }
    fun dismissDialog(){
        this.cancel()
        dialog.dismiss()
        dismiss()
        setOnDismissListener {
            Toast.makeText(context, "dismiss", Toast.LENGTH_SHORT).show()
        }

    }


}
