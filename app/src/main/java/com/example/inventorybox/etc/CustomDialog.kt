package com.example.inventorybox.etc

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.WindowManager
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.layout_custom_dialog.*
import kotlinx.android.synthetic.main.layout_custom_dialog.view.*

class CustomDialog(context: Context) : Dialog(context) {
    val view: View = View.inflate(context, R.layout.layout_custom_dialog, null)

    val builder = AlertDialog.Builder(context).setView(view)

    private var dialog : AlertDialog? = null

    fun setTitle(title : String): CustomDialog{
        view.tv_dialog_title.text = title
        return this
    }
    fun setMessage(message: String) : CustomDialog{
        view.tv_dialog_content.text = message
        return this
    }
    fun setPositiveButton(text : String, listener : (view : View)-> (Unit)) : CustomDialog{
        view.btn_positive.apply{
            this.text = text
            setOnClickListener(listener)
        }
        return this
    }
    fun setNegativeButton(text : String, listener : (view : View) -> (Unit)) : CustomDialog{
        view.btn_negative.apply {
            this.text = text
            setOnClickListener(listener)
        }
        return this
    }
    fun createDialog(){
        dialog = builder.create()
    }
    fun dismissDIalog(){
        dialog?.dismiss()
    }
}
