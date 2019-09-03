package com.maropost.maps.model

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap

class MapsModel(private val mapModelCallback: MapModelCallback) {

    private var mLocationRequest: LocationRequest? = null
    private var currentLocation: Location? = null


    @SuppressLint("MissingPermission")
     fun requestLocationUpdates(activity: Activity,
                                mFusedLocationClient: FusedLocationProviderClient?,
                                mGoogleMap: GoogleMap,
                                REQUEST_CHECK_SETTINGS: Int){
        mLocationRequest = LocationRequest()
        mLocationRequest?.interval = 5000 // two minute interval
        mLocationRequest?.fastestInterval = 6000
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest!!)
        builder.setAlwaysShow(true)
        val locationServices = LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build());
        locationServices.addOnCompleteListener { task ->
            try {
                // The request for location has been provided. Just a simple response callback is provided
                // If required, can have a look at the response then.
                // NOTE -> Location will automatically be fetched because it has already been requested in the call outside the block
                val response = task.getResult(ApiException::class.java)
            }
            catch (exception: ApiException) {
                when(exception.statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->{
                        // Location settings are not satisfied. But could be fixed by showing the user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            val resolvable = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(activity,REQUEST_CHECK_SETTINGS);
                        }
                        catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        }
                        catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    }
                }
            }
        }
        mFusedLocationClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        mGoogleMap.isMyLocationEnabled = true
    }

    fun removeLocationUpdates(mFusedLocationClient: FusedLocationProviderClient) {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }


    /**
     * Callback for location received
     */
    private var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (locationResult.lastLocation == null)
                return
            currentLocation = locationResult.lastLocation
            mapModelCallback.onLocationChanged(currentLocation!!)
        }
    }

    interface MapModelCallback{
        fun onLocationChanged(currentLocation:Location)
    }

}