package com.maropost.timetracker.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.maropost.timetracker.model.LoginModel
import com.maropost.timetracker.pojomodels.Login
import com.maropost.timetracker.utils.Utility

class LoginViewModel: ViewModel(), LoginModel.LoginTokenModelCallback {
    override fun getReportSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReportFailed(failureMessage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val loginModel = LoginModel(this)
    var loginFailedResponse = MutableLiveData<String>()
    var validationStatus = MutableLiveData<ValidationStatus>()
    var loginApiStatus = MutableLiveData<LoginApiStatus>()

    /**
     * Enum validation status types
     */
    enum class ValidationStatus {
        INCOMPLETE_EMAIL,
        INCOMPLETE_PASSWORD,
        INVALID_EMAIL,
        VALID
    }

    /**
     * Enum login status types
     */
    enum class LoginApiStatus {
        SUCCESS,
        FAILURE
    }

    /**
     * Check for empty email and password fields along with valid email content
     */
    fun validateLoginDetails(email: String, password: String){
        if(TextUtils.isEmpty(email))
            validationStatus.value = ValidationStatus.INCOMPLETE_EMAIL
        else if(TextUtils.isEmpty(password))
            validationStatus.value = ValidationStatus.INCOMPLETE_PASSWORD
        else if(!Utility.getInstance().isValidEmail(email))
            validationStatus.value = ValidationStatus.INVALID_EMAIL
        else  validationStatus.value = ValidationStatus.VALID
    }

    /**
     * Call login api from model
     */
    fun performLoginOperation(email: String, password: String){
        loginModel.getLoginToken(email, password)
    }



}