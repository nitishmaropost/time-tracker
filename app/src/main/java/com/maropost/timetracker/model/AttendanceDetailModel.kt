package com.maropost.timetracker.model

import android.text.TextUtils
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

    /**
     * Fetch all the punch records till date
     */
    fun fetchAttendanceDetails() {
        val payload = JSONObject()
        webServiceClient.callWebService(payload, Constants.ATTENDANCE_DETAIL_API, Constants.REQUEST.GET,
            object: WebServiceClient.WebServiceClientCallback{
                override fun onSuccess(responseBody: String) {
                    try {
                        if(!TextUtils.isEmpty(responseBody)) {
                            val json = JSONObject(responseBody)
                            val attendanceDetailsPojo =  Gson().fromJson(json.toString(), AttendanceDetailsPojo::class.java)
                            for (i in 0 until attendanceDetailsPojo.rows.size){
                                arrayList.add(attendanceDetailsPojo.rows[i])
                            }
                            attendanceDetailModelCallback.onSuccess(arrayList)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onFailure(responseBody: String) {
                    try {
                        if(!TextUtils.isEmpty(responseBody)) {
                            attendanceDetailModelCallback.onFailure(responseBody)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
    }

    interface AttendanceDetailModelCallback{
        fun onSuccess(arrayList : ArrayList<Rows>)
        fun onFailure(failureMessage : String)
    }
}