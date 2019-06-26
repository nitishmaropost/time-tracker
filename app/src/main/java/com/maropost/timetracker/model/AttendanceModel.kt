package com.maropost.timetracker.model

import android.os.Handler
import android.text.TextUtils
import com.google.gson.Gson
import com.maropost.timetracker.clients.OkhttpClient
import com.maropost.timetracker.clients.WebServiceClient
import com.maropost.timetracker.pojomodels.Attendance
import com.maropost.timetracker.pojomodels.AttendanceDetails
import com.maropost.timetracker.pojomodels.Rows
import com.maropost.timetracker.utils.Constants
import org.json.JSONObject

class AttendanceModel(private val attendanceModelCallback: AttendanceModel.AttendanceModelCallback) {

    private val webServiceClient = WebServiceClient(OkhttpClient())
    private val handler = Handler()

    /**
     * Get users for admin
     */
    fun fetchUserList() {
        val payload = JSONObject()
        webServiceClient.callWebService(payload,"apiRequestUrl" , Constants.REQUEST.GET,
            object: WebServiceClient.WebServiceClientCallback{
                override fun onSuccess(response: String) {
                    try {
                        if(!TextUtils.isEmpty(response)) {
                            Thread(Runnable {
                                val json = JSONObject(response)
                                val attendance =  Gson().fromJson(json.toString(), Attendance::class.java)
                                handler.post{attendanceModelCallback.onSuccess(attendance)}
                            }).start()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onFailure(responseBody: String) {
                    attendanceModelCallback.onFailure(responseBody)
                }
            })
    }

    interface AttendanceModelCallback{
        fun onSuccess(attendance: Attendance)
        fun onFailure(failureMessage : String)
    }
}