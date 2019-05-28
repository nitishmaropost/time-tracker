package com.maropost.timetracker.clients

import com.maropost.timetracker.R
import com.maropost.timetracker.application.MyApplication
import com.maropost.timetracker.utils.Constants
import com.maropost.timetracker.utils.Utility
import org.json.JSONObject

class WebServiceClient(private var context: Any) {

    fun callWebService(jsonObject: JSONObject, apiRequestUrl: String, requestType: Constants.REQUEST,
                       webServiceClientCallback: WebServiceClientCallback){
        if(context is OkhttpClient && Utility.getInstance().isNetworkConnected()) {
            (context as OkhttpClient).hitWebService(jsonObject, apiRequestUrl, requestType,
                    object : OkhttpClient.WebServiceCallback {
                        override fun onSuccess(responseBody: String) {
                            webServiceClientCallback.onSuccess(responseBody)
                        }
                        override fun onFailure(responseBody: String) {
                            webServiceClientCallback.onFailure(responseBody)
                        }
                    })
        }
        else if(!Utility.getInstance().isNetworkConnected())
            webServiceClientCallback.onFailure(MyApplication.getInstance().getString(R.string.no_internet))
    }

    interface WebServiceClientCallback{
        fun onSuccess(response: String)
        fun onFailure(failureMessage: String)
    }
}