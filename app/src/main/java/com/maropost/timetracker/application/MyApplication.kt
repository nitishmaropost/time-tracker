package com.maropost.timetracker.application

import android.app.Application
import android.text.TextUtils
import com.maropost.timetracker.R
import com.maropost.timetracker.utils.SharedPreferenceHelper
import com.maropost.timetracker.view.activities.MainActivity

class MyApplication : Application() {

    private lateinit var actiity: MainActivity
    var accessToken = ""
    var user_role = ""
    var user_type= USER_TYPE.EMPLOYEE

    /**
     * Enum user types
     */
    enum class USER_TYPE {
        EMPLOYEE,
        ADMIN
    }

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun getInstance(): MyApplication {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (instance == null)
            instance = this
        checkIfLoginTokenAvailable()
        checkIfUserTypeAvailable()
    }


    fun setMainActivityInstance(actiity: MainActivity) {
        this.actiity = actiity
    }

    /**
     * Check if login token is present in preference or not
     * If present assign it in Login Model which will be used as cache till the time app is being viewed.
     * No need to access it time and again from preference.
     */
    private fun checkIfLoginTokenAvailable() {
        val prefernceToken = SharedPreferenceHelper.getInstance().getSharedPreference(
            this,
            getString(R.string.login_token),
            SharedPreferenceHelper.PreferenceDataType.STRING
        ) as String
        if (!TextUtils.isEmpty(prefernceToken))
            accessToken = prefernceToken
    }

    /**
     * Check if user role is present in preference or not
     * If present assign it in Login Model which will be used as cache till the time app is being viewed.
     * No need to access it time and again from preference.
     */
    private fun checkIfUserTypeAvailable() {
        val userRole = SharedPreferenceHelper.getInstance().getSharedPreference(
            this,
            getString(R.string.user_role),
            SharedPreferenceHelper.PreferenceDataType.STRING
        ) as String

        user_role= userRole

        if (!TextUtils.isEmpty(user_role)){

            if(user_role== "employee")
                user_type= USER_TYPE.EMPLOYEE
            if(user_role== "admin")
                user_type= USER_TYPE.ADMIN

        }
    }
}