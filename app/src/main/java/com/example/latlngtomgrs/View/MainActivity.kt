package com.example.latlngtomgrs.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.latlngtomgrs.Contract.MainViewContract
import com.example.latlngtomgrs.Presenter.MainViewPresenter
import com.example.latlngtomgrs.R
import com.example.latlngtomgrs.Utils.Coodinates.MGRS
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainViewContract.View, OnMapReadyCallback {

    private var presenter : MainViewContract.Presenter? = null
    private lateinit var googleMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainViewPresenter()
        presenter!!.setView(this)
        presenter!!.checkPermission(this, this)
        presenter!!.initLocation(this)

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0!!
        presenter!!.initMap(p0)

    }

    override fun setLocation(cameraLocZoom: CameraUpdate) {
        googleMap.moveCamera(cameraLocZoom)
    }

    override fun myLocation(location: LatLng) {
        val mgrs = MGRS()
        val test = mgrs.ConvertGeodeticToMGRS(location)
    }

    override fun onResume() {
        super.onResume()
        if(presenter != null)
            presenter!!.requestLocation()
    }

    override fun onPause() {
        super.onPause()
        if(presenter != null)
            presenter!!.removeLocationListener()
    }
}
