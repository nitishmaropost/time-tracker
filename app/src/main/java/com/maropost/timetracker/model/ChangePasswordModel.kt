package com.maropost.timetracker.model

import android.text.TextUtils
import com.google.gson.Gson
import com.maropost.timetracker.R
import com.maropost.timetracker.application.MyApplication
import com.maropost.timetracker.clients.OkhttpClient
import com.maropost.timetracker.clients.WebServiceClient
import com.maropost.timetracker.pojomodels.Login
import com.maropost.timetracker.utils.Constants
import com.maropost.timetracker.utils.SharedPreferenceHelper
import org.json.JSONObject

class ChangePasswordModel(private val changePasswordModelCallback: ChangePasswordModelCallback) {

    private val webServiceClient = WebServiceClient(OkhttpClient())

    /**
     * WebService hit for change password response details
     */
    fun changePassowordDetails(oldPassword: String, newPassword: String, expireAllSessions: String) {
        val payload = JSONObject()
        payload.put("username", oldPassword)
        payload.put("password", newPassword)
        payload.put("expireAllSessions", expireAllSessions)

        webServiceClient.callWebService(payload, Constants.CHANGE_PASSWORD_API, Constants.REQUEST.POST,
            object : WebServiceClient.WebServiceClientCallback {
                override fun onSuccess(responseBody: String) {
                    try {
                        if (!TextUtils.isEmpty(responseBody)) {
                            val response = JSONObject(responseBody)
                            if (response.has("msg"))
                                changePasswordModelCallback.onUpdateSuccess()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(failureMessage: String) {
                    changePasswordModelCallback.onUpdateFailed(failureMessage)
                }
            })
    }

    interface ChangePasswordModelCallback {
        fun onUpdateSuccess()
        fun onUpdateFailed(failureMessage: String)
    }

}