package com.example.inventorybox.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.service.autofill.Dataset
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import com.example.inventorybox.DatePickerWeekOnly
import com.example.inventorybox.R
import com.super_rabbit.wheel_picker.WheelAdapter
import kotlinx.android.synthetic.main.fragment_graph_detail.*
import kotlinx.android.synthetic.main.graph_datepicker_custom_week_only.*
import java.text.SimpleDateFormat
import java.util.*

class GraphDetail : Fragment() {
    val d : DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day_of_month ->
        Log.d("dialogTest","year = $year, month = $month, date = $day_of_month")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        graph_detail_btn_back.setOnClickListener {
//            val fragment = GraphDetail()
//            val transaction = fragmentManager!!.beginTransaction()
//            transaction.replace(R.id.frame_layout, fragment, "graphDetail").commit()
//        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_graph_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button4.setOnClickListener {

            val pd = DatePickerWeekOnly()
            pd.setListener(d)
            pd.show(requireFragmentManager(), "test")

        }
    }



}
class WPDayPickerAdapter : WheelAdapter {
    //get item value based on item position in wheel
    override fun getValue(position: Int): String {
        if (position == 0)
            return "Today"

        if(position == -1)
            return "Yesterday"

        if (position == 1)
            return "Tomorrow"

        val curDate = Date(System.currentTimeMillis())
        val rightNow = Calendar.getInstance()
        rightNow.time = curDate;
        rightNow.add(Calendar.DATE, position)

        val simpleDateFormat = SimpleDateFormat("MMM d, yyyy")
        return simpleDateFormat.format(rightNow.time)
    }

    //get item position based on item string value
    override fun getPosition(vale: String): Int {
        return 0
    }

    //return a string with the approximate longest text width, for supporting WRAP_CONTENT
    override fun getTextWithMaximumLength(): String {
        return "Mmm 00, 0000"
    }

    //return the maximum index
    override fun getMaxIndex(): Int {
        return Integer.MAX_VALUE
    }

    //return the minimum index
    override fun getMinIndex(): Int {
        return Integer.MIN_VALUE
    }
}