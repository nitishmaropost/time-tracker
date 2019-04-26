package com.maropost.timetracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.maropost.timetracker.R
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.ArrayList

class HomeFragment : MPBaseFragment() {

    private var mView : View?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null)
            mView = inflater.inflate(R.layout.home_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavigationDrawer(true)
        showToolbar(true)
        demoBarChart()
    }

    /**
     * Set selected date from picker
     */
    fun setCalenderDetails(year: Int, month: Int, day: Int){
        val selectedMonth: Int= month+1
        tvDay.text= "Day: $day/$selectedMonth/$year"
    }

    fun demoBarChart() {
        val labels = ArrayList<String>()
        labels.add("Mon")
        labels.add("Tue")
        labels.add("Wed")
        labels.add("Thu")
        labels.add("Fri")

        var arrayList = ArrayList<BarEntry>()
        arrayList.add(BarEntry(9f, 0))
        arrayList.add(BarEntry(8.5f, 1))
        arrayList.add(BarEntry(2f, 2))
        arrayList.add(BarEntry(9f, 3))
        arrayList.add(BarEntry(7f, 4))

        val bardataset = BarDataSet(arrayList,"Days")
        val data = BarData(labels, bardataset)
        bardataset.setColors(ColorTemplate.LIBERTY_COLORS)
        barchart.setData(data)
        barchart.xAxis.isEnabled = false
        barchart.axisLeft.isEnabled = false
        barchart.axisRight.isEnabled = false


    }
}