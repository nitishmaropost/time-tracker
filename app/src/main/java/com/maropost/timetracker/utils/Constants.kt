package com.maropost.timetracker.utils

object Constants {

    var WEB_BASE_URL = "http://localhost:8000/api/v1/"
    const val LOGIN_API = "auth/login"


    /**
     * Enum web service request types
     */
    enum class REQUEST  {
        POST,
        PUT,
        GET
    }
}