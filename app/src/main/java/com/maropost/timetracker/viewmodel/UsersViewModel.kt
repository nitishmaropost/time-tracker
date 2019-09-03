package com.maropost.timetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maropost.timetracker.model.UsersModel
import com.maropost.timetracker.pojomodels.RowShifts

class UsersViewModel : ViewModel(), UsersModel.AttendanceModelCallback {

    private var usersModel = UsersModel(this)
    var arrayList = MutableLiveData<ArrayList<RowShifts>>()
    var failedResponse = MutableLiveData<String>()

    /**
     * Fetch all users
     */
    fun fetchUserList(){
        usersModel.fetchUserList()
    }

    override fun onSuccess(arrayList: ArrayList<RowShifts>) {
        this.arrayList.value = arrayList
    }

    override fun onFailure(failureMessage: String) {
        this.failedResponse.value = failureMessage
    }

}