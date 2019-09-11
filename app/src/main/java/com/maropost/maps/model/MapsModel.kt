package com.maropost.maps.model

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.graphics.Color
import android.location.Location
import android.os.Handler
import android.os.Looper
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.GroundOverlay
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import com.arsy.maps_library.MapRipple

class MapsModel(private val mapModelCallback: MapModelCallback) {

    private var mLocationRequest: LocationRequest? = null
    private var currentLocation: Location? = null
    private var mapRipple : MapRipple ?= null


    /**
     * Get current location
     */
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
            mapRipple?.withLatLng(LatLng(currentLocation!!.latitude, currentLocation!!.longitude))
            mapModelCallback.onLocationChanged(currentLocation!!)
        }
    }


    /**
     * Display an animation effect on location
     */
    fun displayRippleAnimation(mGoogleMap: GoogleMap, latLng: LatLng,context: Context) {

        if(mapRipple != null)
            endRippleAnimation()

        val to = 100
        val fraction = 255 / to
        mapRipple = MapRipple(mGoogleMap, latLng, context)
        mapRipple?.withNumberOfRipples(4)
        mapRipple?.withFillColor(Color.argb((to - 10) * fraction, 48, 118, 254))
        mapRipple?.withStrokewidth(0)
        mapRipple?.withDistance(3.0)
        mapRipple?.withTransparency(0.7f)
        mapRipple?.startRippleMapAnimation()
    }

    fun endRippleAnimation(){
        mapRipple?.stopRippleMapAnimation()
        mapRipple == null
    }


    interface MapModelCallback{
        fun onLocationChanged(currentLocation:Location)
    }

}