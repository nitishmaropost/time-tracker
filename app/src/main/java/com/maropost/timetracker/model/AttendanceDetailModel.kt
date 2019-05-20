package com.maropost.timetracker.model

import android.text.TextUtils
import com.google.gson.Gson
import com.maropost.timetracker.clients.OkhttpClient
import com.maropost.timetracker.clients.WebServiceClient
import com.maropost.timetracker.pojomodels.AttendanceDetailsPojo
import com.maropost.timetracker.utils.Constants
import org.json.JSONObject

class AttendanceDetailModel {

    private val webServiceClient = WebServiceClient(OkhttpClient())

    fun fetchAttendanceDetails() {
        val payload = JSONObject()
        webServiceClient.callWebService(payload, Constants.ATTENDANCE_DETAIL_API, Constants.REQUEST.GET,
            object: WebServiceClient.WebServiceClientCallback{
                override fun onSuccess(responseBody: String) {
                    try {
                        if(!TextUtils.isEmpty(responseBody)) {
                            val json = JSONObject(responseBody)
                            var attendanceDetailsPojo =  Gson().fromJson(json.toString(), AttendanceDetailsPojo::class.java)

                            //loginModelCallback.onLoginTaskSuccess()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onFailure(responseBody: String) {
                    try {
                        if(!TextUtils.isEmpty(responseBody)) {
//                            val json = JSONObject(responseBody)
//                            MaropostApplication.getInstance().user = Gson().fromJson(json.toString(), User::class.java)
//                            loginModelCallback.onLoginFailed(MaropostApplication.getInstance().user.error)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
    }

    interface AttendanceDetailModelCallback{
        fun onSuccess()
    }
}