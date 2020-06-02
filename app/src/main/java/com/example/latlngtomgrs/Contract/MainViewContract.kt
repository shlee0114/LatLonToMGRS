package com.example.latlngtomgrs.Contract

import android.app.Activity
import android.content.Context
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

interface MainViewContract{
    interface View{
        fun setLocation(cameraLocZoom: CameraUpdate)
        fun myLocation(location : LatLng)
    }

    interface Presenter{
        fun setView(view : View)
        fun initMap(map : GoogleMap)
        fun checkPermission(activity: Activity, context : Context)
        fun initLocation(context : Context)
        fun requestLocation()
        fun removeLocationListener()
    }
}