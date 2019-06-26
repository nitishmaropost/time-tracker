package com.maropost.timetracker.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.maropost.timetracker.model.AttendanceModel
import com.maropost.timetracker.pojomodels.Attendance

class AttendanceViewModel : ViewModel(), AttendanceModel.AttendanceModelCallback {

    private var attendanceModel = AttendanceModel(this)
    var arrayList = MutableLiveData<Attendance>()
    var failedResponse = MutableLiveData<String>()

    /**
     * Fetch all users
     */
    fun fetchUserList(){
        attendanceModel.fetchUserList()
    }

    override fun onSuccess(attendance: Attendance) {
        this.arrayList.value = attendance
    }

    override fun onFailure(failureMessage: String) {
        this.failedResponse.value = failureMessage
    }

}