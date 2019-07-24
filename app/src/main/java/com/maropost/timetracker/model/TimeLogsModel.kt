package com.maropost.timetracker.model

import android.os.Handler
import android.text.TextUtils
import com.google.gson.Gson
import com.maropost.timetracker.clients.OkhttpClient
import com.maropost.timetracker.clients.WebServiceClient
import com.maropost.timetracker.pojomodels.TimeLogs
import com.maropost.timetracker.utils.Constants
import com.maropost.timetracker.utils.Utility
import org.json.JSONObject


class TimeLogsModel(private val attendanceDetailModelCallback: AttendanceDetailModelCallback) {

    private val webServiceClient = WebServiceClient(OkhttpClient())
    private val handler = Handler()

    /**
     * Fetch all the punch records till date
     */
    fun fetchAttendanceDetails() {
        callLogsApi(Constants.FETCH_TIME_LOGS)
    }

    /**
     * Get filtered records according to start date and end date
     */
    fun getFilteredAttendanceDetails(startDate: String, endDate: String) {
        val startTimeInMillis = Utility.getInstance().convertDateToMillis(startDate)
        val endTimeInMillis = startTimeInMillis + 86400000 // 24 hrs to millis
        callLogsApi(Constants.FETCH_TIME_LOGS
                + "?" + "start_date=" +startTimeInMillis +"&"+ "end_date="+endTimeInMillis)
    }

    /**
     * Hit web service
     */
    private fun callLogsApi(apiRequestUrl: String){
        val payload = JSONObject()
        webServiceClient.callWebService(payload,apiRequestUrl , Constants.REQUEST.GET,
            object: WebServiceClient.WebServiceClientCallback{
                override fun onSuccess(response: String) {
                    try {
                        if(!TextUtils.isEmpty(response)) {
                            Thread(Runnable {
                                val json = JSONObject(response)
                                val timeLogs=  Gson().fromJson(json.toString(), TimeLogs::class.java)
                                handler.post{attendanceDetailModelCallback.onSuccess(timeLogs)}
                            }).start()
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
        fun onSuccess(timeLogs: TimeLogs)
        fun onFailure(failureMessage : String)
    }
}