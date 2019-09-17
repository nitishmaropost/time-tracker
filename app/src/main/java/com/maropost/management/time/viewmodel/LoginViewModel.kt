package com.maropost.management.time.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.text.TextUtils
import com.maropost.management.time.model.LoginModel
import com.maropost.management.commons.utils.Utility

class LoginViewModel: ViewModel(), LoginModel.LoginTokenModelCallback {

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

    override fun onLoginSuccess() {
        loginApiStatus.value = LoginApiStatus.SUCCESS
    }

    override fun onLoginFailed(failureMessage: String) {
        loginApiStatus.value = LoginApiStatus.FAILURE
        loginFailedResponse.value = failureMessage
    }
}