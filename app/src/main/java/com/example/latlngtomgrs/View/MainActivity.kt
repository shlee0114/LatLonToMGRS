package com.example.latlngtomgrs.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        presenter = MainViewPresenter().apply {
            setView(this@MainActivity)
            checkPermission(this@MainActivity, this@MainActivity)
            initLocation(this@MainActivity)
        }

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap!!
        presenter!!.initMap(googleMap)

    }

    override fun setLocation(cameraLocZoom: CameraUpdate) {
        googleMap.moveCamera(cameraLocZoom)
    }

    override fun myLocation(location: LatLng) {
        val mgrs = MGRS()
        mgrsText.text = mgrs.ConvertGeodeticToMGRS(location)
    }

    override fun onResume() {
        super.onResume()
            presenter?.requestLocation()
    }

    override fun onPause() {
        super.onPause()
            presenter?.removeLocationListener()
    }
}
