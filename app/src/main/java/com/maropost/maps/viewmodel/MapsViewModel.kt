package com.maropost.maps.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.maropost.maps.model.MapsModel

class MapsViewModel: MapsModel.MapModelCallback {

    private var mapsModel:MapsModel = MapsModel(this)
    var currentLocation = MutableLiveData<Location>()

    fun requestLocationUpdates(activity: Activity,
                               mFusedLocationClient: FusedLocationProviderClient?,
                               mGoogleMap: GoogleMap,
                               REQUEST_CHECK_SETTINGS: Int) {
        mapsModel.requestLocationUpdates(activity,mFusedLocationClient,mGoogleMap,REQUEST_CHECK_SETTINGS)
    }


    override fun onLocationChanged(location: Location) {
        currentLocation.value = location
    }

    fun removeLocationUpdates(mFusedLocationClient: FusedLocationProviderClient) {
        mapsModel.removeLocationUpdates(mFusedLocationClient)
    }

}