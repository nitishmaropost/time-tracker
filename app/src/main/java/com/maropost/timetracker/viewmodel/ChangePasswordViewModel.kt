package com.maropost.timetracker.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.maropost.timetracker.model.ChangePasswordModel

class ChangePasswordViewModel: ViewModel(), ChangePasswordModel.ChangePasswordModelCallback {

    private val changePasswordModel = ChangePasswordModel(this)
    var changePasswordFailedResponse = MutableLiveData<String>()
    var validationStatus = MutableLiveData<ValidationStatus>()
    var changePasswordApiStatus = MutableLiveData<ChangePasswordApiStatus>()

    /**
     * Enum validation status types
     */
    enum class ValidationStatus {
        INCOMPLETE_OLD_PASSWORD,
        INCOMPLETE_NEW_PASSWORD,
        INCOMPLETE_CONFIRM_PASSWORD,
        PASSWORDS_UNMATCHED,
        VALID
    }

    /**
     * Enum login status types
     */
    enum class ChangePasswordApiStatus {
        SUCCESS,
        FAILURE
    }

    /**
     * Check for empty password field
     */
    fun validatePassword(oldpassword: String, newpassword: String, confirmpassword: String){
        if(TextUtils.isEmpty(oldpassword) )
            validationStatus.value = ValidationStatus.INCOMPLETE_OLD_PASSWORD
        else if(TextUtils.isEmpty(newpassword) )
            validationStatus.value = ValidationStatus.INCOMPLETE_NEW_PASSWORD
        else if(TextUtils.isEmpty(confirmpassword) )
            validationStatus.value = ValidationStatus.INCOMPLETE_CONFIRM_PASSWORD
        else validateNewPassword(newpassword,confirmpassword)
    }

    /**
     * Check if new and confirm password match or not
     */
    fun validateNewPassword(newPassword: String, confirmPassword: String){
        if (newPassword.equals(confirmPassword,false))
            validationStatus.value = ValidationStatus.VALID
        else
            validationStatus.value= ValidationStatus.PASSWORDS_UNMATCHED

    }

    /**
     * Call change pasword api from model
     */
    fun performChangePasswordOperation(oldPassword: String, newPassword: String, expireAllSessions: String){
        changePasswordModel.changePassowordDetails(oldPassword, newPassword,expireAllSessions)
    }

    override fun onUpdateSuccess() {
        changePasswordApiStatus.value = ChangePasswordApiStatus.SUCCESS
    }

    override fun onUpdateFailed(failureMessage: String) {
        changePasswordApiStatus.value = ChangePasswordApiStatus.FAILURE
        changePasswordFailedResponse.value = failureMessage
    }
}