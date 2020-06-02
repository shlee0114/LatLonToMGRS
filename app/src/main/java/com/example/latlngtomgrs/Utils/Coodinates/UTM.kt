package com.example.latlngtomgrs.Utils.Coodinates

import com.example.latlngtomgrs.Model.ConvertingDataModel
import kotlin.math.PI

class UTM (val dataModel : ConvertingDataModel){
    val trammerc = Trammerc()

    fun ConvertGeodeticToUTM(){
        var LatDegrees : Int
        var LongDegrees : Int
        var tempZone : Int
        var OriginLatitude = 0.0
        var CentralMerdian = 0.0
        var FalseEasting = 500000.0
        var FalseNorthing = 0.0
        var Scale = 0.9996

        if(dataModel.Longitude < 0)
            dataModel.Longitude += (2* PI) + 1.0e-10;
        LatDegrees = (dataModel.Latitude * 180 / PI) as Int
        LongDegrees = (dataModel.Longitude * 180 / PI) as Int

        if(dataModel.Longitude < PI)
            tempZone = (31 + ((dataModel.Longitude * 180 / PI) / 6)) as Int
        else
            tempZone = (((dataModel.Longitude * 180 / PI) / 6) - 29) as Int

        if(tempZone > 60)
            tempZone = 1
    }

}
