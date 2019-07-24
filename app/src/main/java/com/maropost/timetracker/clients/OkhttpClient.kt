package com.maropost.timetracker.clients

import android.annotation.SuppressLint
import okhttp3.*
import org.json.JSONObject
import android.os.AsyncTask
import android.text.TextUtils
import com.maropost.timetracker.application.MyApplication
import com.maropost.timetracker.utils.Constants
import com.maropost.timetracker.utils.Utility

class OkhttpClient {
    private val client = OkHttpClient()
    private val mMediaType = MediaType.parse("application/json")
    private var request: Request? = null
    private var response: Response ?= null

    /**
     * Hit the service and consume the response
     */
    fun hitWebService(jsonObject: JSONObject, apiRequestUrl: String,
                      requestType: Constants.REQUEST, webServiceCallback: WebServiceCallback
    ) {

        val body = RequestBody.create(mMediaType, jsonObject.toString())
        when (requestType) {
            Constants.REQUEST.POST -> {
                postRequest(apiRequestUrl,body)
            }
            Constants.REQUEST.PUT -> {
                putRequest(apiRequestUrl,body)
            }
            Constants.REQUEST.GET -> {
                getRequest(apiRequestUrl)
            }
        }
        if(request != null)
            WebServiceAsync(webServiceCallback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    /**
     * Request for POST
     */
    private fun postRequest(apiRequestUrl: String, body: RequestBody){
        request = Request.Builder()
            .url(Constants.WEB_BASE_URL + apiRequestUrl)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build()
    }

    /**
     * Request for PUT
     */
    private fun putRequest(apiRequestUrl: String, body: RequestBody){
        request =   Request.Builder()
            .url(Constants.WEB_BASE_URL + apiRequestUrl)
            .put(body)
            .addHeader("Content-Type", "application/json")
            .build()
    }

    /**
     * Request for GET
     */
    private fun getRequest(apiRequestUrl: String){

        request = Request.Builder()
            .url(Constants.WEB_BASE_URL + apiRequestUrl)
            .get()
            .addHeader("x-access-token",MyApplication.getInstance().accessToken)
            .build()
    }

    /**
     * Hit web service in background task
     */
    inner class WebServiceAsync(private val webServiceCallback: WebServiceCallback) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void): String? {
            try {
                response = client.newCall(request!!).execute()
                return if(response != null)
                    response!!.body()!!.string()
                else ""
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
        override fun onPostExecute(responseBody: String) {
            super.onPostExecute(responseBody)
            if (!TextUtils.isEmpty(responseBody) && response != null && response!!.isSuccessful) {
                Utility.getInstance().printLog("Response Success -> ",responseBody)
                webServiceCallback.onSuccess(responseBody)
            }
            else if(!TextUtils.isEmpty(responseBody) && response != null && !response!!.isSuccessful) {
                Utility.getInstance().printLog("Response Failure -> ",responseBody)
                webServiceCallback.onFailure(responseBody)
            }
            else {
                Utility.getInstance().printLog("Response Failure -> "," -- ")
                webServiceCallback.onFailure("")
            }
        }
    }

    interface WebServiceCallback {
        fun onSuccess(responseBody: String)
        fun onFailure(failureMessage: String)
    }
}