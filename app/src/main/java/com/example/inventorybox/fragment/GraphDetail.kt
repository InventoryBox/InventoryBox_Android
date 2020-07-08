package com.example.inventorybox.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.FragmentTransaction
import com.example.inventorybox.etc.DatePickerWeekOnly
import com.example.inventorybox.R
import com.example.inventorybox.adapter.GraphDetailWeekCalAdapter
import kotlinx.android.synthetic.main.fragment_graph.*
import kotlinx.android.synthetic.main.fragment_graph_detail.*
import kotlinx.android.synthetic.main.fragment_graph_detail.cal_month
import java.text.SimpleDateFormat
import java.util.*

class GraphDetail : Fragment() {



    val listener: DatePickerDialog.OnDateSetListener = object  : DatePickerDialog.OnDateSetListener{
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, p3: Int) {
            Log.d("datepicker","year = $year, month = $month")
        }
    }
//    val month_listener: OnValueChangeListener = object : OnValueChangeListener{
//        override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
//            cur_month=newVal
//        }
//    }
//    val year_listener :OnValueChangeListener =object : OnValueChangeListener{
//        override fun onValueChange(picker: WheelPicker, oldVal: String, newVal: String) {
//            cur_year=newVal
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_graph_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 뒤로가기 버튼
        btn_back.setOnClickListener {
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.remove(this).commit()
        }
        val cal : Calendar = Calendar.getInstance()

        val format = SimpleDateFormat("MM")

        cal_month.text=format.format(cal.time)
        cal_year.text=cal.get(Calendar.YEAR).toString()

        //누르면 date_picker 뜨도록
        btn_date_picker.setOnClickListener {

            val pd = DatePickerWeekOnly()
            pd.show(requireFragmentManager(), "datePicker")
            pd.setListener(listener)

        }
        graph_detail_week_cal.adapter=GraphDetailWeekCalAdapter(view.context, 5)
    }



}
