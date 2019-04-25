package com.maropost.timetracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.app_bar_main.*
import android.app.DatePickerDialog
import com.maropost.timetracker.R
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet

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
        setToolbarRightIcon(R.drawable.ic_calendar)
        setTitle(resources.getString(R.string.app_name))
        setCalendarListener()
        demoBarChart()
    }

    /*
    Calendar icon listener
    */
    fun setCalendarListener(){
        val imgToolbarCalendarIcon= activity!!.imgToolbarRightIcon as ImageView
         imgToolbarCalendarIcon?.setOnClickListener(View.OnClickListener {
             val c = Calendar.getInstance()
             val year = c.get(Calendar.YEAR)
             val month = c.get(Calendar.MONTH)
             val day = c.get(Calendar.DAY_OF_MONTH)
             val datePickerDialog = DatePickerDialog(activity,
                 DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                     val selectedMonth: Int= month+1
                     tvDay.text= "Day: "+day +"/"+selectedMonth+ "/"+ year
                 }, year, month, day
             )
             datePickerDialog.show()
         })
    }

    fun demoBarChart() {
        val labels = ArrayList<String>()
        labels.add("Fri")
        labels.add("Thu")
        labels.add("Wed")
        labels.add("Tue")
        labels.add("Mon")

        var arrayList = ArrayList<BarEntry>()
        arrayList.add(BarEntry(9f, 0))
        arrayList.add(BarEntry(8.5f, 1))
        arrayList.add(BarEntry(8.8f, 2))
        arrayList.add(BarEntry(9f, 3))
        arrayList.add(BarEntry(7f, 4))

        val bardataset = BarDataSet(arrayList,"Days")
        val data = BarData(labels, bardataset)
        bardataset.setColors(ColorTemplate.LIBERTY_COLORS)
        barchart.setData(data)
        barchart.setDrawGridBackground(false)

    }
}