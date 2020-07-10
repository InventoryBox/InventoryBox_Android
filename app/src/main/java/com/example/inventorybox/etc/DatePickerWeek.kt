package com.example.inventorybox.etc

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.inventorybox.R
import com.super_rabbit.wheel_picker.WheelAdapter
import com.super_rabbit.wheel_picker.WheelPicker
import kotlinx.android.synthetic.main.graph_datepicker_custom_year_to_week.view.*
import java.util.*

class DatePickerWeek() : DialogFragment(){

    private val MAX_YEAR = 2020
    private val MIN_YEAR = 2010
    private var listener : DatePickerDialog.OnDateSetListener? = null

    val cal : Calendar = Calendar.getInstance()
    fun setListener(listener : DatePickerDialog.OnDateSetListener){
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater : LayoutInflater = activity!!.layoutInflater

        val dialog = inflater.inflate(R.layout.graph_datepicker_custom_year_to_week, null)

        val btn = dialog.findViewById<Button>(R.id.graph_custom_dialog_btn)

        val picker_month=dialog.findViewById<WheelPicker>(R.id.picker_month)
        val picker_year=dialog.findViewById<WheelPicker>(R.id.picker_year)
        val picker_week=dialog.findViewById<WheelPicker>(R.id.picker_week)

        // 선택 버튼 누르면 리스너에 data 전달해주기
        btn.setOnClickListener {
            listener?.onDateSet(null, picker_year.getCurrentItem().toInt(), picker_month.getCurrentItem().toInt(), picker_week.getCurrentItem().toInt())
            this.dialog?.cancel()
        }

        // picker 의 min 값과 max값 설정
        picker_month.setMin(1)
        picker_month.setMax(12)
        picker_year.setMin(MIN_YEAR)
        picker_year.setMax(MAX_YEAR)

        picker_week.setMin(1)
        picker_week.setMax(6)


        //현재 날짜 기준으로 스크
        picker_month.scrollToValue((cal.get(Calendar.MONTH)+1).toString())
        picker_year.scrollToValue(cal.get(Calendar.YEAR).toString())
        picker_week.scrollToValue(cal.get(Calendar.WEEK_OF_MONTH).toString())

        builder.setView(dialog)
        return builder.create()
    }

    // dialog 를 밑에 꽉차게 뜨도록
    override fun onResume() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog?.window?.setGravity(Gravity.FILL_HORIZONTAL)
        dialog?.window?.
        setGravity(Gravity.BOTTOM)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onResume()


    }

}