package com.maropost.timetracker.utils

object Constants {

    var WEB_BASE_URL = "http://35.200.185.188/api/v1/"
    const val LOGIN_API = "auth/login"
    const val ATTENDANCE_DETAIL_API = "attendance/logs"
    const val CHANGE_PASSWORD_API = "users/password/update"


    /**
     * Enum web service request types
     */
    enum class REQUEST  {
        POST,
        PUT,
        GET
    }
}