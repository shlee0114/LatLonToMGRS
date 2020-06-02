package com.example.latlngtomgrs.Presenter

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.latlngtomgrs.Contract.MainViewContract
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainViewPresenter : MainViewContract.Presenter{

    private var view : MainViewContract.View? = null
    private var map : GoogleMap? = null
    private var locationRequest : LocationRequest? = null
    private var locationCallback : LocationCallback? = null
    private var fusedLocationProviderClient : FusedLocationProviderClient? = null

    override fun setView(view: MainViewContract.View) {
        this.view = view
    }

    override fun initMap(map : GoogleMap) {
        this.map = map
        val seoul = LatLng(37.536086, 126.989571)
        map.isMyLocationEnabled = true

        view!!.setLocation(CameraUpdateFactory.newLatLngZoom(seoul, 15f))
    }

    override fun checkPermission(activity: Activity, context: Context) {
        val permission =  arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

        var rejectedPermissionList = ArrayList<String>()

        permission.forEach {
            if(ActivityCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED){
                rejectedPermissionList.add(it)
            }
        }

        if(rejectedPermissionList.isNotEmpty()){
                val array = arrayOfNulls<String>(rejectedPermissionList.size)
                ActivityCompat.requestPermissions(activity, rejectedPermissionList.toArray(array), 100)
        }
    }

    override fun initLocation(context : Context) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener {
            if(it != null){
                view!!.setLocation(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
            }
        }
        locationRequest = LocationRequest.create()

        locationRequest.run {
            this!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            this!!.interval =  1 * 1000
        }

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                p0?.let{
                    view!!.myLocation(LatLng(it.locations.last().latitude, it.locations.last().longitude))
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    override fun requestLocation() {
        if(fusedLocationProviderClient == null)
            return
        fusedLocationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    override fun removeLocationListener() {
        fusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
    }
}