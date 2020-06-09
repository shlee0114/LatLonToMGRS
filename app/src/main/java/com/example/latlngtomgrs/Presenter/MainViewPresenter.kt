package com.example.latlngtomgrs.Presenter

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import com.example.latlngtomgrs.R
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.example.latlngtomgrs.Contract.MainViewContract
import com.example.latlngtomgrs.Utils.Coodinates.MGRS
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import java.util.regex.Pattern

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

        view?.setLocation(CameraUpdateFactory.newLatLngZoom(seoul, 15f))
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
                view?.setLocation(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
            }
        }
        locationRequest = LocationRequest.create()

        locationRequest.run {
            this!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            this!!.interval =  1 * 1000
        }

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationresult: LocationResult?) {
                locationresult?.let{
                    val m = MGRS()
                    view?.myLocation(LatLng(it.locations.last().latitude, it.locations.last().longitude), m.ConvertGeodeticToMGRS(LatLng(it.lastLocation.latitude, it.lastLocation.longitude)))
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    override fun requestLocation() {
        fusedLocationProviderClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    override fun removeLocationListener() {
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }

    override fun changeVisible(btn : Button, view : ConstraintLayout, textView : TextView, mode : Boolean, context: Context){
        if(mode){
            view.visibility = View.GONE
            view.animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
            textView.visibility = View.VISIBLE
            btn.visibility = View.VISIBLE
        }else{
            btn.visibility = View.GONE
            textView.visibility = View.GONE
            view.visibility = View.VISIBLE
            view.animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        }
    }

    override fun convertLocation(location: Array<String>, type: Boolean, showResult: Array<TextView>) {
        if(type){
            val m = MGRS()
            location.indices.forEach {
                if(!Pattern.matches("^[0-9].,[0-9]", location[it]))
                    view?.failedConvertLocation(it)
            }

            showResult[0].text = m.ConvertGeodeticToMGRS(LatLng(location[0].toDouble(), location[1].toDouble()))
        }else{

        }
    }
}