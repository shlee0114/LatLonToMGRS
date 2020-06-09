package com.example.latlngtomgrs.Contract

import android.app.Activity
import android.content.Context
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

interface MainViewContract{
    interface View{
        fun setLocation(cameraLocZoom: CameraUpdate)
        fun myLocation(location : LatLng, mrgs : String)
        fun failedConvertLocation(type : Int)
    }

    interface Presenter{
        fun setView(view : View)
        fun initMap(map : GoogleMap)
        fun checkPermission(activity: Activity, context : Context)
        fun initLocation(context : Context)
        fun requestLocation()
        fun removeLocationListener()
        fun changeVisible(btn : Button, view : ConstraintLayout, textView : TextView, mode : Boolean, context: Context)
        fun convertLocation(location : Array<String>, type : Boolean, showResult : Array<TextView>)
    }
}