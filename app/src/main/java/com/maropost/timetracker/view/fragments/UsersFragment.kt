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
import com.maropost.commons.application.MyApplication
import com.maropost.commons.fragments.MPBaseFragment
import com.maropost.timetracker.pojomodels.RowShifts
import com.maropost.timetracker.view.adapters.UsersAdapter
import com.maropost.timetracker.view.adapters.UsersAdapterCallbacks
import com.maropost.timetracker.viewmodel.UsersViewModel
import kotlin.collections.ArrayList

class UsersFragment : MPBaseFragment(), UsersAdapterCallbacks {

    private var mView: View? = null
    private var usersViewModel: UsersViewModel? = null
    private var usersAdapter: UsersAdapter? = null
    private var arrayList: ArrayList<RowShifts>? = null
    private var arrayListTemp: ArrayList<RowShifts>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null)
            mView = inflater.inflate(R.layout.users_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar(true)
        lockNavigationDrawer(true)
        removeToolbarIconLayout()
        setTitle(getString(R.string.users))
        setSearchVisibility(true)
        if (usersViewModel == null) {
            usersViewModel = UsersViewModel()
            usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
            observeLiveData()
            startShimmerAnimation()
            arrayListTemp = ArrayList()
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
        usersViewModel?.failedResponse?.observe(this, Observer { loginFailedResponse ->
            if(!TextUtils.isEmpty(loginFailedResponse))
                showSnackAlert(loginFailedResponse)
            stopShimmerAnimation()
        })
        usersViewModel?.arrayList?.observe(this, Observer { attendanceDetails ->
            this.arrayList?.clear()
            this.arrayList?.addAll(attendanceDetails!!)
            arrayListTemp?.addAll(attendanceDetails!!)
            usersAdapter?.notifyDataSetChanged()
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
        usersAdapter = UsersAdapter(arrayList!!, activity!!,this)
        attendanceRecyclerView.layoutManager = layoutManager
        attendanceRecyclerView.adapter?.setHasStableIds(true)
        attendanceRecyclerView.adapter = usersAdapter
    }

    /**
     * Check if admin then fetch users
     * else fetch particular user records
     */
    private fun validateUserAttendanceRecord() {
        if(MyApplication.getInstance().user_type == MyApplication.USER_TYPE.ADMIN)
        usersViewModel?.fetchUserList()
    }

    /*
    Filter list
    */
    fun filterList(text: String) {
        arrayList?.clear()
        if(TextUtils.isEmpty(text))
            arrayList?.addAll(arrayListTemp!!)
        else {
            for (row in arrayListTemp!!) {
                if (row.user_data?.full_name?.toLowerCase()!!.contains(text.toLowerCase()))
                    arrayList?.add(row)
            }
        }
        usersAdapter?.notifyDataSetChanged()
    }

    override fun onItemClick(rowShifts: RowShifts) {
        val shiftsFragment = ShiftsFragment()
        shiftsFragment.setShiftsData(rowShifts)
        replaceFragment(shiftsFragment,true)
    }
}