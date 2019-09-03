package com.maropost.timetracker.model

import android.text.TextUtils
import com.google.gson.Gson
import com.maropost.timetracker.R
import com.maropost.commons.application.MyApplication
import com.maropost.commons.clients.OkhttpClient
import com.maropost.commons.clients.WebServiceClient
import com.maropost.timetracker.pojomodels.Login
import com.maropost.commons.utils.Constants
import com.maropost.commons.utils.SharedPreferenceHelper
import org.json.JSONObject

class LoginModel(private val loginTokenModelCallback: LoginTokenModelCallback) {

    private val webServiceClient = WebServiceClient(OkhttpClient())
    private var loginResponseList = ArrayList<Login>()

    /**
     * WebService hit for Login token response details
     */
    fun getLoginToken(email: String, password: String) {
        val payload = JSONObject()
        payload.put("username", email)
        payload.put("password", password)

        webServiceClient.callWebService(payload, Constants.LOGIN_API, Constants.REQUEST.POST,
            object : WebServiceClient.WebServiceClientCallback {
                override fun onSuccess(responseBody: String) {
                    try {
                        if (!TextUtils.isEmpty(responseBody)) {
                            val response = JSONObject(responseBody)

                            //sharedpreference & cache variable
                            val login = Gson().fromJson(response.toString(), Login::class.java)
                            MyApplication.getInstance().accessToken = login.token //access token
                            saveLoginTokenInPreference(login.token,login.user_info!!.user_role)
                            loginTokenModelCallback.onLoginSuccess()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(failureMessage: String) {
                    loginTokenModelCallback.onLoginFailed(failureMessage)
                }
            })
    }

    /**
     * Save login token in preference
     */
    private fun saveLoginTokenInPreference(loginToken: String, user_role: String) {
        SharedPreferenceHelper.getInstance()
            .setSharedPreference(
                MyApplication.getInstance(),
                MyApplication.getInstance().getString(R.string.login_token), loginToken)
        SharedPreferenceHelper.getInstance()
            .setSharedPreference(
                MyApplication.getInstance(),
                MyApplication.getInstance().getString(R.string.user_role), user_role)
    }

    interface LoginTokenModelCallback {
        fun onLoginSuccess()
        fun onLoginFailed(failureMessage: String)
    }

}