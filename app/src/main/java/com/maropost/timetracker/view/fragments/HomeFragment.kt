package com.maropost.timetracker.view.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.TimeUtils
import com.maropost.timetracker.view.adapters.TimeDetailsAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : MPBaseFragment() {

    private var mView : View?= null
    //private var homeViewModel : HomeViewModel? = null
    private var timeDetailsAdapter : TimeDetailsAdapter?= null
    private var timeDetailsList = ArrayList<TimeUtils>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null)
            mView = inflater.inflate(R.layout.home_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavigationDrawer(true)
        setCurrentDate()
        showToolbar(true)
        initializeRecyclerView()
        demoBarChart()
    }

    /**
     * Set current date on launch
     */
    private fun setCurrentDate(){
        val c = Calendar.getInstance()
        val df = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = df.format(c.time)
        tvDay.text= "Today, "+formattedDate


        /*final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            return "Today " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString();
        }*/

    }

    /**
     * Set selected date from picker
     */
    fun setCalenderDetails(year: Int, month: Int, day: Int){
        val selectedMonth: Int= month+1
        tvDay.text= "$day/$selectedMonth/$year"
    }

    /*
    * Initialize recyclerview and set adapter
    */
    private fun initializeRecyclerView(){
        val layoutManager= LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL,false)
        timeDetailsAdapter = TimeDetailsAdapter(timeDetailsList, activity!!)
        timeDetailsRecyclerview.layoutManager = layoutManager
        timeDetailsRecyclerview.adapter = timeDetailsAdapter
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