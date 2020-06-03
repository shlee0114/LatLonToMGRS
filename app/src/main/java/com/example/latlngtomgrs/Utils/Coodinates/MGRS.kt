package com.example.latlngtomgrs.Utils.Coodinates

import com.example.latlngtomgrs.Model.ConvertingDataModel
import com.example.latlngtomgrs.Model.CoordinatesData
import com.google.android.gms.maps.model.LatLng
import kotlin.math.PI

class MGRS() {

    private lateinit var mgrs : String
    private lateinit var UTM : UTM

    fun ConvertGeodeticToMGRS(location : LatLng) : String{

        val latitude = location.latitude * PI / 180
        val longitude = location.longitude * PI / 180

        val dataModel = ConvertingDataModel(latitude,longitude,0,'0',0.0,0.0)

        UTM = UTM(dataModel, CoordinatesData.MGRS_a, CoordinatesData.MGRS_f, 0)

        if ((latitude < CoordinatesData.PI_OVER_2*-1) || (latitude > CoordinatesData.PI_OVER_2))
        {
            return "";
        }
        if ((longitude < PI*-1) || (longitude > (2 * PI)))
        {
            return "";
        }

        UTM

        return mgrs
    }
}