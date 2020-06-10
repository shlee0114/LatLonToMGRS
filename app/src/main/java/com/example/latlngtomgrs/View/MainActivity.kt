package com.example.latlngtomgrs.View

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.example.latlngtomgrs.Contract.MainViewContract
import com.example.latlngtomgrs.Presenter.MainViewPresenter
import com.example.latlngtomgrs.R
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainViewContract.View, OnMapReadyCallback {

    private val presenter : MainViewContract.Presenter by lazy {
        MainViewPresenter().apply {
            setView(this@MainActivity)
            checkPermission(this@MainActivity, this@MainActivity)
            initLocation(this@MainActivity)
        }
    }
    private lateinit var googleMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        btnUp.setOnClickListener{
            presenter.changeVisible(btnUp, inInfoView, nowLocationInfo_, false, this)
        }
        btnDown.setOnClickListener {
            presenter.changeVisible(btnUp, inInfoView, nowLocationInfo_, true, this)
        }

        ChLatLon.setOnClickListener {
            presenter.convertLocation(arrayOf(lat.text.toString(), lon.text.toString()), true, arrayOf(getMGRS))
        }

        lon.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                presenter.convertLocation(arrayOf(lat.text.toString(), lon.text.toString()), true, arrayOf(getMGRS))
                true
            }else{
                false
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap!!
        presenter.initMap(googleMap)

    }

    override fun setLocation(cameraLocZoom: CameraUpdate) {
        googleMap.moveCamera(cameraLocZoom)
    }

    override fun myLocation(location: LatLng, mgrs : String) {
        nowLocationInfo.text = "Latitude : "+ location.latitude+"  Longitude : " + location.longitude +"\nMGRS : "+mgrs
        nowLocationInfo_.text =  "Latitude : "+ location.latitude+"  Longitude : " + location.longitude +"\nMGRS : "+mgrs
    }

    override fun onResume() {
        super.onResume()
            presenter.requestLocation()
    }

    override fun onPause() {
        super.onPause()
            presenter.removeLocationListener()
    }

    override fun failedConvertLocation(type: Int) {

    }
}
