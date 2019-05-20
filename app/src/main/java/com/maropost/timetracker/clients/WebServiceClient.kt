package com.maropost.timetracker.clients

import com.maropost.timetracker.utils.Constants
import org.json.JSONObject

class WebServiceClient(private var context: Any) {

    fun callWebService(jsonObject: JSONObject, apiRequestUrl: String, requestType: Constants.REQUEST,
                       webServiceClientCallback: WebServiceClientCallback){
        if(context is OkhttpClient) {
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
    }

    interface WebServiceClientCallback{
        fun onSuccess(response: String)
        fun onFailure(failureMessage: String)
    }
}