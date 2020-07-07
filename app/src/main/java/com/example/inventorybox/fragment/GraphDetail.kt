package com.example.inventorybox.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.example.inventorybox.etc.DatePickerWeekOnly
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.fragment_graph_detail.*

class GraphDetail : Fragment() {

    var cur_month = ""
    var cur_year = ""

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
//            pd.setListener(d)
            pd.show(requireFragmentManager(), "test")
//            pd.setListener(listener_month=month_listener, listener_year = year_listener)
            pd.setListener(listener)

        }
    }



}
