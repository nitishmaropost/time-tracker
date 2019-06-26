package com.maropost.timetracker.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.maropost.timetracker.model.AttendanceDetailModel
import com.maropost.timetracker.pojomodels.AttendanceDetails
import com.maropost.timetracker.pojomodels.Rows

class AttendanceDetailViewModel : ViewModel(), AttendanceDetailModel.AttendanceDetailModelCallback {

    private var attendanceDetailModel = AttendanceDetailModel(this)
    var arrayList = MutableLiveData<AttendanceDetails>()
    var failedResponse = MutableLiveData<String>()

    /**
     * Fetch all the punch records till date
     */
    fun fetchAttendanceDetails() {
        attendanceDetailModel.fetchAttendanceDetails()
    }

    override fun onSuccess(attendanceDetails: AttendanceDetails) {
    this.arrayList.value = attendanceDetails
    }

    override fun onFailure(failureMessage: String) {
        this.failedResponse.value = failureMessage
    }

    /**
     * Get data according to date selected from calendar
     */
    fun getFilteredAttendanceDetails(startDate: String, endDate: String) {
        attendanceDetailModel.getFilteredAttendanceDetails(startDate, endDate)
    }
}