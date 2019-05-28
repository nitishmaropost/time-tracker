package com.maropost.timetracker.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    private var mLastClickTime: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null)
            mView = inflater.inflate(R.layout.attendance_detail_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar(true)
      //  setToolbarIconVisibility(false)
        removeOldToolbarIcons()
        setToolbarIcon()
        setTitle(getString(R.string.time_logs))
        shimmer_view_container.startShimmerAnimation()
        if (attendanceDetailViewModel == null) {
            attendanceDetailViewModel = AttendanceDetailViewModel()
            attendanceDetailViewModel = ViewModelProviders.of(this).get(AttendanceDetailViewModel::class.java)
            observeLiveData()
            arrayList = ArrayList<Rows>()
            initializeRecyclerView()
            fetchAttendanceDetails()
            setBottomSheet()
        }
    }

    private fun setBottomSheet() {
        val behavior = BottomSheetBehavior.from<View>(design_bottom_sheet)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> Log.i(
                        "BottomSheetCallback",
                        "BottomSheetBehavior.STATE_DRAGGING"
                    )
                    BottomSheetBehavior.STATE_SETTLING -> Log.i(
                        "BottomSheetCallback",
                        "BottomSheetBehavior.STATE_SETTLING"
                    )
                    BottomSheetBehavior.STATE_EXPANDED -> Log.i(
                        "BottomSheetCallback",
                        "BottomSheetBehavior.STATE_EXPANDED"
                    )
                    BottomSheetBehavior.STATE_COLLAPSED -> Log.i(
                        "BottomSheetCallback",
                        "BottomSheetBehavior.STATE_COLLAPSED"
                    )
                    BottomSheetBehavior.STATE_HIDDEN -> Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_HIDDEN")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.i("BottomSheetCallback", "slideOffset: $slideOffset")
            }
        })

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
                if(arrayList!!.isEmpty())
                    showSnackAlert(getString(R.string.no_record_found))

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
        if(arrayList!!.isEmpty())
            txtNoRecord.visibility = View.VISIBLE
        else txtNoRecord.visibility = View.GONE
    }
}