package com.maropost.timetracker.model

import android.os.Handler
import android.text.TextUtils
import com.google.gson.Gson
import com.maropost.timetracker.clients.OkhttpClient
import com.maropost.timetracker.clients.WebServiceClient
import com.maropost.timetracker.pojomodels.AttendanceDetails
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
                            Thread(Runnable {
                                val json = JSONObject(response)
                                val attendanceDetails=  Gson().fromJson(json.toString(), AttendanceDetails::class.java)
                                /*for (i in 0 until attendanceDetails.rows.size){
                                    attendanceDetails.rows[i].pinTypeText = (attendanceDetails.pin_type_text as JsonObject)
                                        .get(attendanceDetails.rows[i].pin_type.toString()).asString
                                    arrayList.add(attendanceDetails.rows[i])
                                }*/
                               // arrayList.addAll(attendanceDetails.rows)
                                handler.post{attendanceDetailModelCallback.onSuccess(attendanceDetails)}
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
        fun onSuccess(attendanceDetails: AttendanceDetails)
        fun onFailure(failureMessage : String)
    }
}