package com.maropost.timetracker.pojomodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Attendance {

    @SerializedName("users")
    @Expose
    var users : Map<String, String> ?= null
}