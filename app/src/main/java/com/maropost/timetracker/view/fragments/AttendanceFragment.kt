package com.maropost.timetracker.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.maropost.timetracker.R
import com.maropost.timetracker.application.MyApplication
import com.maropost.timetracker.pojomodels.Attendance
import com.maropost.timetracker.pojomodels.RowShifts
import com.maropost.timetracker.view.adapters.AttendanceAdapter
import com.maropost.timetracker.viewmodel.AttendanceViewModel
import kotlinx.android.synthetic.main.attendance_fragment.*
import java.util.*

class AttendanceFragment : MPBaseFragment(){

    private var mView: View? = null
    private var attendanceViewModel: AttendanceViewModel? = null
    private var attendanceAdapter: AttendanceAdapter? = null
    private var arrayList: ArrayList<RowShifts>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null)
            mView = inflater.inflate(R.layout.attendance_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar(true)
        removeToolbarIconLayout()
        setTitle("")
        startShimmerAnimation()
        if (attendanceViewModel == null) {
            attendanceViewModel = AttendanceViewModel()
            attendanceViewModel = ViewModelProviders.of(this).get(AttendanceViewModel::class.java)
            observeLiveData()
            arrayList = ArrayList()
            initializeRecyclerView()
            initialiseListeners()
            validateUserAttendanceRecord()
        }
    }

    /**
     * Observe live data values
     */
    private fun observeLiveData() {
        attendanceViewModel?.failedResponse?.observe(this, Observer { loginFailedResponse ->
            if(!TextUtils.isEmpty(loginFailedResponse))
                showSnackAlert(loginFailedResponse)
            stopShimmerAnimation()
        })

        attendanceViewModel?.arrayList?.observe(this, Observer { attendanceDetails ->
            this.arrayList?.clear()
            this.arrayList?.addAll(attendanceDetails!!)
            attendanceAdapter?.notifyDataSetChanged()
            stopShimmerAnimation()
        })
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

    /**
     * Setup listeners
     */
    private fun initialiseListeners() {
        txtRetry.setOnClickListener{
            startShimmerAnimation()
            validateUserAttendanceRecord()
        }
    }

    /*
    * Initialize recycler view and set adapter
    */
    private fun initializeRecyclerView() {
        val layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        attendanceAdapter = AttendanceAdapter(arrayList!!, activity!!)
        attendanceRecyclerView.layoutManager = layoutManager
        attendanceRecyclerView.adapter?.setHasStableIds(true)
        attendanceRecyclerView.adapter = attendanceAdapter
    }

    /**
     * Check if admin then fetch users
     * else fetch particular user records
     */
    private fun validateUserAttendanceRecord() {
        if(MyApplication.getInstance().user_type == MyApplication.USER_TYPE.ADMIN)
        attendanceViewModel?.fetchUserList()
    }
}