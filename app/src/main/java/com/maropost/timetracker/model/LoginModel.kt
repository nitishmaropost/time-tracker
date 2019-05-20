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
                            MyApplication.getInstance().accessToken = login.token
                            saveLoginTokenInPreference(login.token)
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
    private fun saveLoginTokenInPreference(loginToken: String?) {
        SharedPreferenceHelper.getInstance()
            .setSharedPreference(MyApplication.getInstance(),
                MyApplication.getInstance().getString(R.string.login_token), loginToken!!)
    }

    interface LoginTokenModelCallback {
        fun onLoginSuccess()
        fun onLoginFailed(failureMessage: String)
    }

}