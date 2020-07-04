package com.example.inventorybox.graph

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class CustomBarDataSet : BarDataSet {
    constructor(y_vals:List<BarEntry>, label:String) : super(y_vals,label)


    override fun getColor(index: Int): Int {
        if(getEntryForIndex(index).y>5){
            return mColors.get(1)
        }else{
            return mColors.get(0)
        }

    }

    override fun getEntryIndex(e: BarEntry?): Int {
        return super.getEntryIndex(e)
    }

}