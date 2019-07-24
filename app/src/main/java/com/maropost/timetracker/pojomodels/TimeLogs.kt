package com.maropost.timetracker.pojomodels

import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TimeLogs {

    var rows = ArrayList<Rows>()
    @SerializedName("pin_type_text")
    @Expose
    var pinTypeTextMap : Map<String, String> ?= null

    /*"pin_type_text": {
        "1": "In Fingerprint",
        "4": "In Card",
        "101": "Out Fingerprint",
        "104": "Out Card"
    }*/
}