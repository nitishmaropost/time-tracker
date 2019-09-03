package com.maropost.timetracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maropost.commons.fragments.MPBaseFragment
import com.maropost.timetracker.R
import com.maropost.timetracker.viewmodel.HomeViewModel


class HomeFragment : MPBaseFragment() {

    private var mView : View?= null
    private var homeViewModel : HomeViewModel? = null
    /*private var timeDetailsAdapter : TimeDetailsAdapter?= null
    private var timeDetailsList = ArrayList<TimeUtils>()*/
    private var mLastClickTime: Long = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null)
            mView = inflater.inflate(R.layout.home_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.app_name))
        lockNavigationDrawer(false)
        showToolbar(true)
        removeToolbarIconLayout()
        //setToolbarIcon()
        setSearchVisibility(false)

        if(homeViewModel == null) {
            homeViewModel = HomeViewModel()
            initialiseListener()
            //setCurrentDate()
            //initializeRecyclerView()
            //demoBarChart()
        }
    }

    /**
     * Add calendar icon
     */
  /*  private fun setToolbarIcon() {
        val params = DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT)
        val image = ImageView(activity)
        image.layoutParams = params
        image.setImageResource(R.drawable.ic_calendar)
        params.setMargins(5, 5, 10, 5)
        setToolbarIconLayout(image)

        image.setOnClickListener{
            if (SystemClock.elapsedRealtime() - mLastClickTime > 1000) {
                mLastClickTime = SystemClock.elapsedRealtime()
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    activity!!,
                    DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
                        setCalenderDetails(mYear, mMonth, mDay)
                    }, year, month, day
                )
                datePickerDialog.show()
            }
        }
    }*/

    /**
     * Setup listeners
     */
    private fun initialiseListener() {
        /*imgDetails.setOnClickListener{changeFragment(TimeLogsFragment.DATETYPE.NONE)}
        lnrTodayBubble.setOnClickListener{ changeFragment(TimeLogsFragment.DATETYPE.TODAY) }
        lnrWeekBubble.setOnClickListener{ changeFragment(TimeLogsFragment.DATETYPE.WEEKLY) }
        lnrMonthBubble.setOnClickListener{ *//*changeFragment(TimeLogsFragment.DATETYPE.MONTHLY)*//*
        replaceFragment(UsersFragment(),true)}*/
    }

    /**
     * Replace current frag to TimeLogsFragment along with date type
     */
   /* private fun changeFragment(dateType: TimeLogsFragment.DATETYPE){
        val attendanceDetailFragment = TimeLogsFragment()
        attendanceDetailFragment.setDateType(dateType)
        replaceFragment(attendanceDetailFragment,true)
    }*/

    /**
     * Set current date on launch
     */
   /* @SuppressLint("SetTextI18n")
    private fun setCurrentDate(){
        val calendar = Calendar.getInstance()
        tvDay.text = getString(R.string.today) + " " + Utility.getInstance().getCurrentDate(calendar)
    }*/

    /**
     * Set selected date from picker
     */
  /*  @SuppressLint("SimpleDateFormat")
    fun setCalenderDetails(year: Int, month: Int, day: Int){
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate: String= sdf.format(calendar.time)
        formatToTodayYesterday(formattedDate)
    }*/

    /*
    Display today/yesterday against date
    */
 /*   @SuppressLint("SimpleDateFormat")
    fun formatToTodayYesterday(formattedDate: String){
        val currentDate = Date(System.currentTimeMillis())
        val mydate = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val today: String= dateFormat.format(currentDate)
        val yesterday: String = dateFormat.format(mydate)
        when {
            formattedDate == yesterday -> tvDay.text= "Yesterday, $formattedDate"
            formattedDate == today -> tvDay.text= "Today, $formattedDate"
            else -> tvDay.text= "$formattedDate"
        }
    }*/


    /*
    * Initialize recyclerview and set adapter
    */
    /*private fun initializeRecyclerView(){
        val layoutManager= LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL,false)
        timeDetailsAdapter = TimeDetailsAdapter(timeDetailsList, activity!!)
        timeDetailsRecyclerview.layoutManager = layoutManager
        timeDetailsRecyclerview.adapter = timeDetailsAdapter
    }*/

    /*fun demoBarChart() {
        val labels = ArrayList<String>()
        labels.add("Sun")
        labels.add("Mon")
        labels.add("Tue")
        labels.add("Wed")
        labels.add("Thu")
        labels.add("Fri")
        labels.add("Sat")

        val arrayList = ArrayList<BarEntry>()
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
        bardataset.setColors(ColorTemplate.PASTEL_COLORS)
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
    }*/
}