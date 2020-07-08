package com.example.latlngtomgrs.Presenter

import android.Manifest
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
import java.lang.Exception
import java.util.regex.Pattern

class MainViewPresenter : MainViewContract.Presenter{

    private var view : MainViewContract.View? = null
    private var map : GoogleMap? = null
    private var locationRequest : LocationRequest? = null
    private var locationCallback : LocationCallback? = null
    private var fusedLocationProviderClient : FusedLocationProviderClient? = null

    var context : Context? = null

    override fun setView(view: MainViewContract.View) {
        this.view = view
    }

    override fun initMap(map : GoogleMap) {
        this.map = map
        val seoul = LatLng(37.536086, 126.989571)

        view?.setLocation(CameraUpdateFactory.newLatLngZoom(seoul, 15f))
        if(context == null)
            return
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        map.isMyLocationEnabled = true
    }

    override fun checkPermission(activity: Activity, context: Context) {
        val permission =  arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION
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

        this.context = context

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener {
                    if(it != null){
                        view?.setLocation(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
                    }
                }
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener {
                    if(it != null){
                        view?.setLocation(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
                    }
                }
            return
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
        if(context == null)
            return
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
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
        val m = MGRS()
        if(type){
            location.indices.forEach {
                try{
                    location[it].toDouble()
                }catch (e : Exception){
                    view?.failedConvertLocation(it)
                    return
                }
            }
            view?.setLocation(CameraUpdateFactory.newLatLngZoom(LatLng(location[0].toDouble(), location[1].toDouble()), 15f))

            showResult[0].text = m.ConvertGeodeticToMGRS(LatLng(location[0].toDouble(), location[1].toDouble()))
        }else{
            val latLng = m.MGRSToUTM(location[0])

            if(latLng == null){
                view?.failedConvertLocation(3)
                return
            }

            showResult[0].text = latLng?.latitude.toString()
            showResult[1].text = latLng?.longitude.toString()

        }
    }
}