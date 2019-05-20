package com.maropost.timetracker.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.maropost.timetracker.model.AttendanceDetailModel
import com.maropost.timetracker.pojomodels.Rows

class AttendanceDetailViewModel : ViewModel() {
    private var attendanceDetailModel = AttendanceDetailModel()
    private var arrayList = MutableLiveData<ArrayList<Rows>>()

    fun fetchAttendanceDetails() {
        attendanceDetailModel.fetchAttendanceDetails()
    }
}