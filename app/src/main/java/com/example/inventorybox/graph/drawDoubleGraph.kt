package com.example.inventorybox.graph


import android.content.Context
import androidx.core.content.ContextCompat
import com.example.inventorybox.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

fun BarChart.drawDoubleGraph(context:Context, data1: ArrayList<Int>,data2 : ArrayList<Int>){


    var values1 = ArrayList<BarEntry>()
    var values2 = ArrayList<BarEntry>()


    // data 만들
    for(i in 0..6){
        values1.add(BarEntry(i.toFloat(),data1.get(i).toFloat()))
        values2.add(BarEntry(i.toFloat(),data2.get(i).toFloat()))
    }

    val data_set1 =BarDataSet(values1,"")
    val data_set2 =BarDataSet(values2, "")

    data_set1.color= getColorFromRes(context, R.color.grey)
    data_set2.color= getColorFromRes(context, R.color.yellow)


    val data_sets = ArrayList<IBarDataSet>()
    data_sets.add(data_set1)
    data_sets.add(data_set2)

    val datas = BarData(data_sets)

    datas.setValueFormatter(object :ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return Math.round(value).toString()
        }
    })

    datas.barWidth=0.15f
    this.data=datas
    this.invalidate()
    this.groupBars(-0.5f, 0.5f, 0.1f)


    setAxis(this)
    //legend 제거
    this.legend.isEnabled=false
    //동그란 모
    val renderer=RoundedChartRenderer(this, this.animator, this.viewPortHandler)
    renderer.setmRadius(30f)
    this.renderer = renderer

    this.description.isEnabled=false

    var max = this.yChartMax
    drawAxisLine(this, max.toInt())
    drawAxisLine(this, max.toInt()/2)



}

fun setAxis(barchart:BarChart) {
    val x_axis = barchart.xAxis
    val left_axis = barchart.axisLeft
    val right_axis = barchart.axisRight

    val DAYS= arrayListOf<String>("일","월","화","수","목","금","토")

    //x축에 일-월 표시
    x_axis.valueFormatter=object :ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return DAYS.get(value.toInt())
        }
    }
    //label은 바닥에 위치하도록
    x_axis.position=XAxis.XAxisPosition.BOTTOM
    x_axis.setDrawGridLines(false)

    left_axis.setDrawGridLines(false)
    left_axis.setDrawLabels(false)
    left_axis.setDrawAxisLine(false)

    right_axis.isEnabled=false



//    left_axis.isEnabled=false
//
//
//    right_axis.setDrawLabels(false)
//    right_axis.setDrawAxisLine(false)
//    right_axis.isEnabled=false)

}

fun getColorFromRes(context: Context, color : Int) :Int{
    return ContextCompat.getColor(context, color)
}
private fun drawAxisLine(barchart: BarChart, num : Int) {
    val line : LimitLine = LimitLine(num.toFloat())
    barchart.axisLeft.addLimitLine(line)
//    line.lineColor= getColorFromRes(R.color.yellow)

}