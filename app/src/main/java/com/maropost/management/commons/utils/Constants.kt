package com.maropost.management.commons.utils

object Constants {

    var WEB_BASE_URL = "http://35.200.185.188/api/v1/"
    const val LOGIN_API = "auth/login"
    const val FETCH_TIME_LOGS = "attendance/logs"
    const val FETCH_SHIFT_RECORDS_API = "attendance/shifts"


    /**
     * Enum web service request types
     */
    enum class REQUEST  {
        POST,
        PUT,
        GET
    }
}