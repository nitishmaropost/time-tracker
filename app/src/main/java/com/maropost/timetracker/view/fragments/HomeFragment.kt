package com.maropost.timetracker.view.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.XAxis
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
        fetchAttendanceDetails()
    }

    private fun fetchAttendanceDetails() {

    }

    /**
     * Set current date on launch
     */
    private fun setCurrentDate(){
        val c = Calendar.getInstance()
        val df = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = df.format(c.time)
        tvDay.text= "Today, "+formattedDate
    }

    /**
     * Set selected date from picker
     */
    fun setCalenderDetails(year: Int, month: Int, day: Int){
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate: String= sdf.format(calendar.getTime())
        formatToTodayYesterday(formattedDate)

    }

    /*
    Display today/yesterday against date
    */
    fun formatToTodayYesterday(formattedDate: String){
        val currentDate = Date(System.currentTimeMillis())
        val mydate = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val today: String= dateFormat.format(currentDate)
        val yesterday: String = dateFormat.format(mydate)

        if(formattedDate.equals(yesterday)){
            tvDay.text= "Yesterday, $formattedDate"
        }
        else if(formattedDate.equals(today)){
            tvDay.text= "Today, $formattedDate"
        }
        else
            tvDay.text= "$formattedDate"
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
        labels.add("Sun")
        labels.add("Mon")
        labels.add("Tue")
        labels.add("Wed")
        labels.add("Thu")
        labels.add("Fri")
        labels.add("Sat")

        var arrayList = ArrayList<BarEntry>()
        arrayList.add(BarEntry(9f, 0))
        arrayList.add(BarEntry(8.5f, 1))
        arrayList.add(BarEntry(2f, 2))
        arrayList.add(BarEntry(9f, 3))
        arrayList.add(BarEntry(7f, 4))
        arrayList.add(BarEntry(5f, 5))
        arrayList.add(BarEntry(8f, 6))

        val bardataset = BarDataSet(arrayList,"Days")
        bardataset.setDrawValues(false)
        val data = BarData(labels, bardataset)

        data.isHighlightEnabled = false
        bardataset.setColors(ColorTemplate.LIBERTY_COLORS)
        barchart.data = data
        barchart.xAxis.isEnabled = true
        barchart.axisLeft.isEnabled = true
        barchart.axisRight.isEnabled = false
        barchart.xAxis.textSize = 12f
        barchart.setDescription("")
        barchart.legend.isEnabled = false
        barchart.xAxis.setDrawGridLines(false)
        barchart.axisLeft.setDrawGridLines(false)
        barchart.axisRight.setDrawGridLines(false)
        barchart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barchart.animateY(3000)

    }
}