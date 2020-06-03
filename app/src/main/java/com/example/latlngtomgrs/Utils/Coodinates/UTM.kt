package com.example.latlngtomgrs.Utils.Coodinates

import com.example.latlngtomgrs.Model.ConvertDataModelTrammerc
import com.example.latlngtomgrs.Model.ConvertingDataModel
import kotlin.math.PI

class UTM (val dataModel : ConvertingDataModel, var UTM_a : Double, var UTM_f : Double, var UTM_override : Int){
    lateinit var trammerc : Trammerc

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

        if(UTM_override != 0){
            if(tempZone == 1 && UTM_override == 60)
                tempZone = UTM_override
            else if(tempZone == 60 && UTM_override == 1)
                tempZone = UTM_override
            else if((tempZone -1) <= UTM_override && UTM_override <= (tempZone + 1))
                tempZone = UTM_override
        }

        if(tempZone >= 31)
            CentralMerdian = (6 * tempZone - 183) * PI / 180
        else
            CentralMerdian = (6 * tempZone - 177) * PI / 180

        dataModel.Zone = tempZone

        if(dataModel.Latitude < 0){
            FalseNorthing = 10000000.0
            dataModel.Hemisphere = 'S'
        }
        else
            dataModel.Hemisphere = 'N'
        trammerc = Trammerc(dataModel,
            ConvertDataModelTrammerc( UTM_a, UTM_f, OriginLatitude, CentralMerdian, FalseEasting, FalseNorthing, Scale))

    }

}
