package com.maropost.timetracker.model

import android.os.Handler
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.maropost.timetracker.clients.OkhttpClient
import com.maropost.timetracker.clients.WebServiceClient
import com.maropost.timetracker.pojomodels.AttendanceDetailsPojo
import com.maropost.timetracker.pojomodels.Rows
import com.maropost.timetracker.utils.Constants
import org.json.JSONObject

class AttendanceDetailModel(private val attendanceDetailModelCallback: AttendanceDetailModelCallback) {

    private val webServiceClient = WebServiceClient(OkhttpClient())
    private var arrayList = ArrayList<Rows>()
    private val handler = Handler()

    /**
     * Fetch all the punch records till date
     */
    fun fetchAttendanceDetails() {
        val payload = JSONObject()
        webServiceClient.callWebService(payload, Constants.ATTENDANCE_DETAIL_API, Constants.REQUEST.GET,
            object: WebServiceClient.WebServiceClientCallback{
                override fun onSuccess(response: String) {
                    try {
                        if(!TextUtils.isEmpty(response)) {
                            val json = JSONObject(response)
                            val attendanceDetailsPojo =  Gson().fromJson(json.toString(), AttendanceDetailsPojo::class.java)
                            arrayList.addAll(attendanceDetailsPojo.rows)
                            attendanceDetailModelCallback.onSuccess(arrayList)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onFailure(responseBody: String) {
                    attendanceDetailModelCallback.onFailure(responseBody)
                }
            })
    }

    interface AttendanceDetailModelCallback{
        fun onSuccess(arrayList : ArrayList<Rows>)
        fun onFailure(failureMessage : String)
    }
}