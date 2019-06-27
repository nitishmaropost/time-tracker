package com.maropost.timetracker.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.maropost.timetracker.model.AttendanceModel
import com.maropost.timetracker.pojomodels.Attendance
import com.maropost.timetracker.pojomodels.RowShifts

class AttendanceViewModel : ViewModel(), AttendanceModel.AttendanceModelCallback {

    private var attendanceModel = AttendanceModel(this)
    var arrayList = MutableLiveData<ArrayList<RowShifts>>()
    var failedResponse = MutableLiveData<String>()

    /**
     * Fetch all users
     */
    fun fetchUserList(){
        attendanceModel.fetchUserList()
    }

    override fun onSuccess(arrayList: ArrayList<RowShifts>) {
        this.arrayList.value = arrayList
    }

    override fun onFailure(failureMessage: String) {
        this.failedResponse.value = failureMessage
    }

}