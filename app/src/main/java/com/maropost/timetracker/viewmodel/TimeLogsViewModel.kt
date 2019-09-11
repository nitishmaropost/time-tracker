package com.maropost.timetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maropost.timetracker.model.TimeLogsModel
import com.maropost.timetracker.pojomodels.TimeLogs

class TimeLogsViewModel : ViewModel(), TimeLogsModel.AttendanceDetailModelCallback {

    private var timeLogsModel = TimeLogsModel(this)
    var arrayList = MutableLiveData<TimeLogs>()
    var failedResponse = MutableLiveData<String>()

    /**
     * Fetch all the punch records till date
     */
    fun fetchAttendanceDetails() {
        timeLogsModel.fetchAttendanceDetails()
    }

    override fun onSuccess(attendanceDetails: TimeLogs) {
    this.arrayList.value = attendanceDetails
    }

    override fun onFailure(failureMessage: String) {
        this.failedResponse.value = failureMessage
    }

    /**
     * Get data according to date selected from calendar
     */
    fun getFilteredAttendanceDetails(startDate: String, endDate: String) {
        timeLogsModel.getFilteredAttendanceDetails(startDate, endDate)
    }
}