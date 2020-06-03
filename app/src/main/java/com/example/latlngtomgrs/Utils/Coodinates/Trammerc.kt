package com.example.latlngtomgrs.Utils.Coodinates

import com.example.latlngtomgrs.Model.ConvertDataModelTrammerc
import com.example.latlngtomgrs.Model.ConvertingDataModel
import com.example.latlngtomgrs.Model.CoordinatesData

class Trammerc(val dataModel : ConvertingDataModel, val TranmercDataModel : ConvertDataModelTrammerc) {

    fun init(){
        ConvertGeodeticToTransverseMercator(CoordinatesData.MAX_LAT, CoordinatesData.MAX_DELTA_LONG)
        ConvertGeodeticToTransverseMercator(0.0, CoordinatesData.MAX_DELTA_LONG)
    }

    fun ConvertGeodeticToTransverseMercator(Latitude : Double, Longitude : Double){

    }
}