package com.maropost.timetracker.model

import android.os.Handler
import android.text.TextUtils
import com.google.gson.Gson
import com.maropost.commons.clients.OkhttpClient
import com.maropost.commons.clients.WebServiceClient
import com.maropost.timetracker.pojomodels.Attendance
import com.maropost.timetracker.pojomodels.RowShifts
import com.maropost.commons.utils.Constants
import org.json.JSONObject

class UsersModel(private val attendanceModelCallback: AttendanceModelCallback) {

    private val webServiceClient = WebServiceClient(OkhttpClient())
    private val handler = Handler()

    /**
     * Get users for admin
     */
    fun fetchUserList(/*emp_code: String, startDate: String, endDate: String*/) {
        val payload = JSONObject()
        val apiRequestUrl = Constants.FETCH_SHIFT_RECORDS_API + /*"?emp_code=" +
                "176" + */ "?&start_date=" + "1558204200000" + "&end_date=" + "1558722600000"
        webServiceClient.callWebService(payload, apiRequestUrl, Constants.REQUEST.GET,
            object: WebServiceClient.WebServiceClientCallback{
                override fun onSuccess(response: String) {
                    try {
                        if(!TextUtils.isEmpty(response)) {
                            Thread(Runnable {
                                val json = JSONObject(response)
                                val attendance =  Gson().fromJson(json.toString(), Attendance::class.java)
                                handler.post{attendanceModelCallback.onSuccess(attendance.rowShifts)}
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
        fun onSuccess(arrayList: ArrayList<RowShifts>)
        fun onFailure(failureMessage : String)
    }
}