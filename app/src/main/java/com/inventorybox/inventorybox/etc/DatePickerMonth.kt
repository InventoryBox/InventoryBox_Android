package com.inventorybox.inventorybox.etc

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
import com.inventorybox.inventorybox.R
import com.super_rabbit.wheel_picker.WheelPicker
import java.text.SimpleDateFormat
import java.util.*

class DatePickerMonth() : DialogFragment(){

    var currentTime = Calendar.getInstance().time
    var yearFormat: SimpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())

    private val MAX_YEAR = yearFormat.format(currentTime).toInt();
    private val MIN_YEAR = 2010
    lateinit var picker_month :WheelPicker
    lateinit var picker_year :WheelPicker
    private var listener : DatePickerDialog.OnDateSetListener? = null

    val cal : Calendar = Calendar.getInstance()
    fun setListener(listener : DatePickerDialog.OnDateSetListener){
        this.listener = listener
}

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater : LayoutInflater = activity!!.layoutInflater

        val dialog = inflater.inflate(R.layout.graph_datepicker_custom_year_month, null)

        val btn = dialog.findViewById<Button>(R.id.graph_custom_dialog_btn)

        picker_month=dialog.findViewById<WheelPicker>(R.id.picker_month)
        picker_year=dialog.findViewById<WheelPicker>(R.id.picker_year)

        // 선택 버튼 누르면 리스너에 data 전달해주기
        btn.setOnClickListener {
            listener?.onDateSet(null, picker_year.getCurrentItem().toInt(), picker_month.getCurrentItem().toInt(), 0)
            this.dialog?.cancel()
        }

        // picker 의 min 값과 max값 설정
        picker_month.setMin(1)
        picker_month.setMax(12)
        picker_year.setMin(MIN_YEAR)
        picker_year.setMax(MAX_YEAR)

        //현재 날짜 기준으로 스크
        picker_month.scrollToValue((cal.get(Calendar.MONTH)+1).toString())
        picker_year.scrollToValue(cal.get(Calendar.YEAR).toString())

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