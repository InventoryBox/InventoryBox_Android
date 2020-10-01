package com.inventorybox.inventorybox.graph

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class CustomBarDataSet : BarDataSet {

    var count_noti = 0
    constructor(y_vals:List<BarEntry>, label:String, count_noti:Int) : super(y_vals,label){
        this.count_noti = count_noti
    }


    override fun getColor(index: Int): Int {
        if(getEntryForIndex(index).y>count_noti){
            return mColors.get(1)
        }else{
            return mColors.get(0)
        }

    }

    override fun getEntryIndex(e: BarEntry?): Int {
        return super.getEntryIndex(e)
    }

}