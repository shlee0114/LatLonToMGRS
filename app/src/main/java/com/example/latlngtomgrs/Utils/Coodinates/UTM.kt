package com.example.latlngtomgrs.Utils.Coodinates

import com.example.latlngtomgrs.Model.ConvertDataModelTrammerc
import com.example.latlngtomgrs.Model.ConvertingDataModel
import kotlin.math.PI

class UTM (val dataModel : ConvertingDataModel, var UTM_a : Double, var UTM_f : Double){
    lateinit var trammerc : Trammerc

    fun ConvertGeodeticToUTM(){
        val LatDegrees : Int
        val LongDegrees : Int
        var tempZone : Int
        val CentralMerdian : Double
        var FalseNorthing = 0.0

        if(dataModel.Longitude < 0)
            dataModel.Longitude += (2* PI) + 1.0e-10;
        LatDegrees = (dataModel.Latitude * 180 / PI).toInt()
        LongDegrees = (dataModel.Longitude * 180 / PI).toInt()

        if(dataModel.Longitude < PI)
            tempZone = (31 + ((dataModel.Longitude * 180 / PI) / 6)).toInt()
        else
            tempZone = (((dataModel.Longitude * 180 / PI) / 6) - 29).toInt()

        if(tempZone > 60)
            tempZone = 1

        if(LatDegrees in 56..63 && LongDegrees in 0..2)
            tempZone = 31
        if(LatDegrees in 56..63 && LongDegrees in 3..11)
            tempZone = 32
        if(LatDegrees > 71) {
            if (LongDegrees in 0..8)
                tempZone = 31
            if (LongDegrees in 9..20)
                tempZone = 33
            if(LongDegrees in 21..32)
                tempZone = 35
            if(LongDegrees in 33..41)
                tempZone = 37
        }

        CentralMerdian = if(tempZone >= 31)
            (6 * tempZone - 183) * PI / 180
        else
            (6 * tempZone - 177) * PI / 180

        dataModel.Zone = tempZone

        if(dataModel.Latitude < 0){
            FalseNorthing = 10000000.0
            dataModel.Hemisphere = 'S'
        }
        else
            dataModel.Hemisphere = 'N'
        trammerc = Trammerc(dataModel,
            ConvertDataModelTrammerc( UTM_a, UTM_f, 0.0, CentralMerdian, 500000.0, FalseNorthing, 0.9996))
        trammerc.init()
        trammerc.ConvertGeodeticToTransverseMercator(dataModel.Latitude, dataModel.Longitude, 2)
    }

}
