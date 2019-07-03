package com.maropost.timetracker.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.Rows
import com.maropost.timetracker.utils.Utility
import com.maropost.timetracker.view.adapters.AttendanceDetailAdapter
import com.maropost.timetracker.viewmodel.AttendanceDetailViewModel
import kotlinx.android.synthetic.main.attendance_detail_fragment.*
import java.util.*
import com.bumptech.glide.Glide



class AttendanceDetailFragment : MPBaseFragment(), BottomSheetFragment.BottomSheetCallbacks {

    private var mView: View? = null
    private var attendanceDetailAdapter: AttendanceDetailAdapter? = null
    private var arrayList: ArrayList<Rows>? = null
    private var attendanceDetailViewModel: AttendanceDetailViewModel? = null
    private var mLastClickTime: Long = 0
    private var bottomSheetFragment :BottomSheetFragment ?= null
    private var startDate = ""
    private var endDate = ""
    private var dateType = DATETYPE.NONE

    enum class DATETYPE{
        NONE,
        TODAY,
        WEEKLY,
        MONTHLY
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null)
            mView = inflater.inflate(R.layout.attendance_detail_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar(true)
        removeOldToolbarIcons()
        setToolbarIcon()
        setSearchVisibility(false)
        setTitle(getString(R.string.time_logs))
        if (attendanceDetailViewModel == null) {
            attendanceDetailViewModel = AttendanceDetailViewModel()
            attendanceDetailViewModel = ViewModelProviders.of(this).get(AttendanceDetailViewModel::class.java)
            observeLiveData()
            startShimmerAnimation()
            arrayList = ArrayList()
            initializeRecyclerView()
            validateDateType()
            initialiseListeners()
        }
    }

    /**
     * Get result based on date type
     */
    private fun validateDateType(){
        when(dateType){
            DATETYPE.NONE    -> fetchAttendanceDetails()
            DATETYPE.TODAY   -> getCurrentDayDateValues()
            DATETYPE.WEEKLY  -> getCurrentWeekDateValues()
            DATETYPE.MONTHLY -> getCurrentMonthDateValues()
        }
    }

    /**
     * Start date and End date = current date
     */
    private fun getCurrentDayDateValues() {
        getCurrentDate()
        onDateSelected(startDate,endDate)
    }

    /**
     * Get the date for the same day
     */
    private fun getCurrentDate() {
        val calendar = Calendar.getInstance()
        startDate = Utility.getInstance().getCurrentDate(calendar)
        endDate = startDate
    }

    /**
     * Get first and last date of current week
     */
    private fun getCurrentWeekDateValues() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK,calendar.firstDayOfWeek)
        startDate = Utility.getInstance().getCurrentDate(calendar)
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        endDate = Utility.getInstance().getCurrentDate(calendar)
        onDateSelected(startDate,endDate)
    }

    /**
     * Start date and End date = current month first and last date
     */
    private fun getCurrentMonthDateValues() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH,1)
        startDate = Utility.getInstance().getCurrentDate(calendar)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        endDate = Utility.getInstance().getCurrentDate(calendar)
        onDateSelected(startDate,endDate)
    }

    /**
     * Setup listeners
     */
    private fun initialiseListeners() {
        txtRetry.setOnClickListener{
            startShimmerAnimation()
            if(!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate))
                attendanceDetailViewModel?.getFilteredAttendanceDetails(startDate,endDate)
            else getCurrentWeekDateValues()
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
    private fun setToolbarIcon() {
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
    }

    /**
     * Observe live data values
     */
    private fun observeLiveData() {
        attendanceDetailViewModel?.failedResponse?.observe(this, Observer { loginFailedResponse ->
            if(!TextUtils.isEmpty(loginFailedResponse))
                showSnackAlert(loginFailedResponse)
            stopShimmerAnimation()
        })

        attendanceDetailViewModel?.arrayList?.observe(this, Observer { attendanceDetails ->
            this.arrayList?.clear()
            this.arrayList?.addAll(attendanceDetails!!.rows)
            attendanceDetailAdapter?.setModel(attendanceDetails!!)
            attendanceDetailAdapter?.notifyDataSetChanged()
            stopShimmerAnimation()
        })
    }

    /*
    * Initialize recycler view and set adapter
    */
    private fun initializeRecyclerView() {
        val layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        attendanceDetailAdapter = AttendanceDetailAdapter(arrayList!!, activity!!)
        detailRecyclerView.layoutManager = layoutManager
        detailRecyclerView.adapter?.setHasStableIds(true)
        detailRecyclerView.adapter = attendanceDetailAdapter
    }

    /**
     * Fetch all the punch records till date
     */
    private fun fetchAttendanceDetails() {
        attendanceDetailViewModel?.fetchAttendanceDetails()
        getCurrentDate()
    }

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
        attendanceDetailViewModel?.getFilteredAttendanceDetails(startDate,endDate)
    }

    /**
     * Set date type selected from home screen
     */
    fun setDateType(dateType: DATETYPE) {
        this.dateType = dateType
    }
}