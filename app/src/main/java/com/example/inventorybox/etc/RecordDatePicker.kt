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
import kotlinx.android.synthetic.main.record_datepicker.*
import java.util.*

class RecordDatePicker() : DialogFragment(){

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

        val dialog = inflater.inflate(R.layout.record_datepicker, null)

        val btn = dialog.findViewById<Button>(R.id.datepicker_btn)

        val picker_month=dialog.findViewById<WheelPicker>(R.id.picker_month)
        val picker_year=dialog.findViewById<WheelPicker>(R.id.picker_year)
        val picker_day=dialog.findViewById<WheelPicker>(R.id.picker_day)

        // 선택 버튼 누르면 리스너에 data 전달해주기
        datepicker_btn.setOnClickListener {
            listener?.onDateSet(null, picker_year.getCurrentItem().toInt(), picker_month.getCurrentItem().toInt(), picker_day.getCurrentItem().toInt())
            this.dialog?.cancel()
        }

        // picker 의 min 값과 max값 설정
        picker_month.setMin(1)
        picker_month.setMax(12)
        picker_year.setMin(MIN_YEAR)
        picker_year.setMax(MAX_YEAR)
        picker_day.setMin(1)
        picker_day.setMax(30)


        //현재 날짜 기준로 datepicker 설정 - 서버에서 받은 날짜로 datepicker 설정으로 변경
        picker_month.scrollToValue((cal.get(Calendar.MONTH)+1).toString())
        picker_year.scrollToValue(cal.get(Calendar.YEAR).toString())
        picker_day.scrollToValue(cal.get(Calendar.WEEK_OF_MONTH).toString())

        //picker의 day의 최대, 최소값 정하기

        cal.set(picker_year.getCurrentItem().toInt(),  picker_month.getCurrentItem().toInt(),1)
        picker_day.setMax(Calendar.DAY_OF_MONTH)



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