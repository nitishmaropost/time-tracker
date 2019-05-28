package com.maropost.timetracker.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.Rows
import com.maropost.timetracker.view.adapters.AttendanceDetailAdapter
import com.maropost.timetracker.viewmodel.AttendanceDetailViewModel
import kotlinx.android.synthetic.main.attendance_detail_fragment.*
import java.util.*


class AttendanceDetailFragment : MPBaseFragment() {
    private var mView: View? = null
    private var attendanceDetailAdapter: AttendanceDetailAdapter? = null
    private var arrayList: ArrayList<Rows>? = null
    private var attendanceDetailViewModel: AttendanceDetailViewModel? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null)
            mView = inflater.inflate(R.layout.attendance_detail_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar(true)
        setToolbarIconVisibility(false)
        setTitle(getString(R.string.time_logs))
        shimmer_view_container.startShimmerAnimation()
        if (attendanceDetailViewModel == null) {
            attendanceDetailViewModel = AttendanceDetailViewModel()
            attendanceDetailViewModel = ViewModelProviders.of(this).get(AttendanceDetailViewModel::class.java)
            observeLiveData()
            arrayList = ArrayList<Rows>()
            initializeRecyclerView()
            fetchAttendanceDetails()
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
            if (attendanceDetails?.rows!!.isNotEmpty())
                this.arrayList?.addAll(attendanceDetails.rows)
            attendanceDetailAdapter?.setModel(attendanceDetails)
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
    }

    /**
     * Stop shimmer animation
     */
    private fun stopShimmerAnimation(){
        shimmer_view_container.stopShimmerAnimation()
        shimmer_view_container.visibility = View.GONE
    }
}