package com.example.inventorybox

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerWeekOnly : DialogFragment(){
    private val MAX_YEAR = 2020
    private val MIN_YEAR = 2010

    private lateinit var listener : DatePickerDialog.OnDateSetListener
    val cal : Calendar = Calendar.getInstance()
    fun setListener(listener : DatePickerDialog.OnDateSetListener){
        this.listener=listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater : LayoutInflater = activity!!.layoutInflater

        val dialog = inflater.inflate(R.layout.graph_datepicker_custom_week_only, null)

        val btn = dialog.findViewById<Button>(R.id.graph_custom_dialog_btn)
        val picker_month = dialog.findViewById<NumberPicker>(R.id.picker_month)
        val picker_year = dialog.findViewById<NumberPicker>(R.id.picker_year)

        btn.setOnClickListener {
            listener.onDateSet(null, picker_year.value, picker_month.value, 0)
            this.dialog!!.cancel()
        }

        // picker 의 min 값과 max값 설정
        picker_month.minValue=1
        picker_month.maxValue=12
        picker_month.value= cal.get(Calendar.MONTH)+1

        val year = cal.get(Calendar.YEAR)
        picker_year.minValue=MIN_YEAR
        picker_year.maxValue=MAX_YEAR
        picker_year.value = year

        builder.setView(dialog)
        return builder.create()
    }
//
    override fun onResume() {
    val width = resources.getDimensionPixelSize(R.dimen.popup_width)
    val height = resources.getDimensionPixelSize(R.dimen.popup_height)
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setGravity(Gravity.FILL_HORIZONTAL)
        dialog?.window?.
        setGravity(Gravity.BOTTOM)
    dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog?.window?.setLayout(width, height)
        super.onResume()


    }

}
