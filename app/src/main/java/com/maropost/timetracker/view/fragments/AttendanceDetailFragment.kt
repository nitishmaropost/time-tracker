package com.maropost.timetracker.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maropost.timetracker.R
import com.maropost.timetracker.pojomodels.AttendanceDetailsPojo
import com.maropost.timetracker.pojomodels.Rows
import com.maropost.timetracker.utils.Utility
import com.maropost.timetracker.view.adapters.AttendanceDetailAdapter
import com.maropost.timetracker.viewmodel.AttendanceDetailViewModel
import com.maropost.timetracker.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.attendance_detail_fragment.*

class AttendanceDetailFragment : MPBaseFragment() {
    private var mView : View ?= null
    private var attendanceDetailAdapter : AttendanceDetailAdapter?= null
    private var arrayList : ArrayList<Rows> ?= null
    private var attendanceDetailViewModel : AttendanceDetailViewModel ?= null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null)
            mView = inflater.inflate(R.layout.attendance_detail_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavigationDrawer(true)
        showToolbar(false)
        if(attendanceDetailViewModel == null ) {
            attendanceDetailViewModel = AttendanceDetailViewModel()
            attendanceDetailViewModel = ViewModelProviders.of(this).get(AttendanceDetailViewModel::class.java)
            observeLiveData()
            arrayList = ArrayList<Rows>()
            initializeRecyclerView()
            fetchAttendanceDetails()
        }
    }

    private fun observeLiveData() {
        attendanceDetailViewModel?.failedResponse?.observe(this, Observer { loginFailedResponse ->
            Utility.getInstance().showToast(activity!!,loginFailedResponse!!)
        })

        attendanceDetailViewModel?.arrayList?.observe(this, Observer { list ->
            if(list!!.isNotEmpty())
            this.arrayList?.addAll(list)
            attendanceDetailAdapter?.notifyDataSetChanged()
        })
    }

    /*
    * Initialize recyclerview and set adapter
    */
    private fun initializeRecyclerView(){
        val layoutManager= LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL,false)
        attendanceDetailAdapter = AttendanceDetailAdapter(arrayList!!, activity!!)
        detailRecyclerView.layoutManager = layoutManager
        detailRecyclerView.adapter = attendanceDetailAdapter
    }

    /**
     * Fetch all the punch records till date
     */
    private fun fetchAttendanceDetails() {
        attendanceDetailViewModel?.fetchAttendanceDetails()
    }
}