package com.maropost.timetracker.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.maropost.timetracker.model.AttendanceDetailModel
import com.maropost.timetracker.pojomodels.Rows

class AttendanceDetailViewModel : ViewModel(), AttendanceDetailModel.AttendanceDetailModelCallback {

    private var attendanceDetailModel = AttendanceDetailModel(this)
    var arrayList = MutableLiveData<ArrayList<Rows>>()
    var failedResponse = MutableLiveData<String>()

    /**
     * Fetch all the punch records till date
     */
    fun fetchAttendanceDetails() {
        attendanceDetailModel.fetchAttendanceDetails()
    }

    override fun onSuccess(arrayList: ArrayList<Rows>) {
    this.arrayList.value = arrayList
    }

    override fun onFailure(failureMessage: String) {
        this.failedResponse.value = failureMessage
    }
}