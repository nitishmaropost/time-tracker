package com.maropost.timetracker.view.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.maropost.commons.fragments.MPBaseFragment
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.Rows
import com.maropost.timetracker.pojomodels.Shifts
import com.maropost.timetracker.view.adapters.TimeLogsAdapter
import com.maropost.timetracker.viewmodel.TimeLogsViewModel
import kotlinx.android.synthetic.main.time_logs_fragment.*
import java.util.*


class TimeLogsFragment : MPBaseFragment(), BottomSheetFragment.BottomSheetCallbacks {

    private var mView: View? = null
    private var timeLogsAdapter: TimeLogsAdapter? = null
    private var arrayList: ArrayList<Rows>? = null
    private var timeLogsViewModel: TimeLogsViewModel? = null
    private var mLastClickTime: Long = 0
    private var bottomSheetFragment :BottomSheetFragment ?= null
    private var startDate = ""
    private var endDate = ""
    private var shifts: Shifts ?= null
    //private var dateType = DATETYPE.NONE

   /* enum class DATETYPE{
        NONE,
        TODAY,
        WEEKLY,
        MONTHLY
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null)
            mView = inflater.inflate(R.layout.time_logs_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar(true)
        removeOldToolbarIcons()
        //setToolbarIcon()
        setSearchVisibility(false)
        setTitle(getString(R.string.time_logs))
        if (timeLogsViewModel == null) {
            timeLogsViewModel = TimeLogsViewModel()
            timeLogsViewModel = ViewModelProviders.of(this).get(TimeLogsViewModel::class.java)
            observeLiveData()
            startShimmerAnimation()
            arrayList = ArrayList()
            initializeRecyclerView()
            //validateDateType()
            initialiseListeners()
            fetchTimeLogs()
        }
    }

    /**
     * Fetch time logs for a particular day based on start date and end date from Shifts model
     */
    private fun fetchTimeLogs() {
        if(shifts != null && !TextUtils.isEmpty(shifts!!.dated_str)) {
            startDate = shifts!!.dated_str
            timeLogsViewModel?.getFilteredAttendanceDetails(startDate, "")
        }
    }

    /**
     * Get result based on date type
     */
   /* private fun validateDateType(){
        when(dateType){
            DATETYPE.NONE    -> fetchAttendanceDetails()
            DATETYPE.TODAY   -> getCurrentDayDateValues()
            DATETYPE.WEEKLY  -> getCurrentWeekDateValues()
            DATETYPE.MONTHLY -> getCurrentMonthDateValues()
        }
    }*/

    /**
     * Start date and End date = current date
     */
    /*private fun getCurrentDayDateValues() {
        getCurrentDate()
        onDateSelected(startDate,endDate)
    }*/

    /**
     * Get the date for the same day
     */
   /* private fun getCurrentDate() {
        val calendar = Calendar.getInstance()
        startDate = Utility.getInstance().getCurrentDate(calendar)
        endDate = startDate
    }*/

    /**
     * Get first and last date of current week
     */
    /*private fun getCurrentWeekDateValues() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK,calendar.firstDayOfWeek)
        startDate = Utility.getInstance().getCurrentDate(calendar)
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        endDate = Utility.getInstance().getCurrentDate(calendar)
        onDateSelected(startDate,endDate)
    }*/

    /**
     * Start date and End date = current month first and last date
     */
    /*private fun getCurrentMonthDateValues() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH,1)
        startDate = Utility.getInstance().getCurrentDate(calendar)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        endDate = Utility.getInstance().getCurrentDate(calendar)
        onDateSelected(startDate,endDate)
    }*/

    /**
     * Setup listeners
     */
    private fun initialiseListeners() {
        txtRetry.setOnClickListener{
            startShimmerAnimation()
            if(!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate))
                timeLogsViewModel?.getFilteredAttendanceDetails(startDate,endDate)
            //else getCurrentWeekDateValues()
        }
    }

    /**
     * Remove any item icon in toolbar
     */
    private fun removeOldToolbarIcons() {
        removeToolbarIconLayout()
    }

    /**
     * Add calendar icon
     */
   /* private fun setToolbarIcon() {
        val params = DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT)
        val image = ImageView(activity)
        image.layoutParams = params
        image.setImageResource(R.drawable.ic_funnel)
        params.setMargins(5, 5, 10, 5)
        setToolbarIconLayout(image)
        image.setOnClickListener{
            if (SystemClock.elapsedRealtime() - mLastClickTime > 1000) {
                mLastClickTime = SystemClock.elapsedRealtime()
                if(bottomSheetFragment == null) {
                    bottomSheetFragment = BottomSheetFragment()
                    bottomSheetFragment?.setCallback(this)
                }
                bottomSheetFragment?.setDate(startDate,endDate)
                bottomSheetFragment!!.show(fragmentManager, "TAG")
            }
        }
    }*/

    /**
     * Observe live data values
     */
    private fun observeLiveData() {
        timeLogsViewModel?.failedResponse?.observe(this, Observer { loginFailedResponse ->
            if(!TextUtils.isEmpty(loginFailedResponse))
                showSnackAlert(loginFailedResponse)
            stopShimmerAnimation()
        })

        timeLogsViewModel?.arrayList?.observe(this, Observer { attendanceDetails ->
            this.arrayList?.clear()
            this.arrayList?.addAll(attendanceDetails!!.rows)
            timeLogsAdapter?.setModel(attendanceDetails!!)
            timeLogsAdapter?.notifyDataSetChanged()
            stopShimmerAnimation()
        })
    }

    /*
    * Initialize recycler view and set adapter
    */
    private fun initializeRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            activity!!,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        timeLogsAdapter = TimeLogsAdapter(arrayList!!, activity!!)
        detailRecyclerView.layoutManager = layoutManager
        detailRecyclerView.adapter?.setHasStableIds(true)
        detailRecyclerView.adapter = timeLogsAdapter
    }

    /**
     * Fetch all the punch records till date
     */
    /*private fun fetchAttendanceDetails() {
        timeLogsViewModel?.fetchAttendanceDetails()
        getCurrentDate()
    }*/

    /**
     * Start shimmer animation
     */
    private fun startShimmerAnimation(){
        shimmer_view_container.startShimmerAnimation()
        shimmer_view_container.visibility = View.VISIBLE
        imgNoResultFound.visibility = View.GONE
        txtRetry.visibility = View.GONE
    }

    /**
     * Stop shimmer animation
     */
    private fun stopShimmerAnimation(){
        shimmer_view_container.stopShimmerAnimation()
        shimmer_view_container.visibility = View.GONE
        if(arrayList!!.isEmpty()) {
            imgNoResultFound.visibility = View.VISIBLE
            Glide
                .with(activity!!)
                .load(R.drawable.no_result)
                .into(imgNoResultFound)
            txtRetry.visibility = View.VISIBLE
        }
        else {
            imgNoResultFound.visibility = View.GONE
            txtRetry.visibility = View.GONE
        }
    }

    override fun onDateSelected(startDate: String, endDate: String) {
        if(bottomSheetFragment != null)
            bottomSheetFragment?.dismiss()
        this.startDate = startDate
        this.endDate = endDate
        timeLogsViewModel?.getFilteredAttendanceDetails(startDate,endDate)
    }

    fun setShifts(shifts: Shifts) {
        this.shifts = shifts
    }

    /**
     * Set date type selected from home screen
     */
    /*fun setDateType(dateType: DATETYPE) {
        this.dateType = dateType
    }*/
}