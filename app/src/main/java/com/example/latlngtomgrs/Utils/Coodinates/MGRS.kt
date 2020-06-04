package com.example.latlngtomgrs.Utils.Coodinates

import com.example.latlngtomgrs.Model.ConvertingDataModel
import com.example.latlngtomgrs.Model.CoordinatesData
import com.google.android.gms.maps.model.LatLng
import kotlin.math.PI
import kotlin.math.truncate

class MGRS() {

    private lateinit var mgrs : String
    private lateinit var UTM : UTM
    private lateinit var dataModel : ConvertingDataModel

    fun ConvertGeodeticToMGRS(location : LatLng) : String{

        val latitude = location.latitude * PI / 180
        val longitude = location.longitude * PI / 180

        dataModel = ConvertingDataModel(latitude,longitude,0,'0',0.0,0.0)

        UTM = UTM(dataModel, CoordinatesData.MGRS_a, CoordinatesData.MGRS_f)

        if ((latitude < CoordinatesData.PI_OVER_2*-1) || (latitude > CoordinatesData.PI_OVER_2)){
            return "";
        }
        if ((longitude < PI*-1) || (longitude > (2 * PI))){
            return "";
        }

        UTM.ConvertGeodeticToUTM()

        return UTMToMGRS()
    }

    private fun UTMToMGRS() : String{
        val letters = arrayOfNulls<Long>(3)

        dataModel.Easting = RoundMGRS(dataModel.Easting).toDouble()
        dataModel.Northing = RoundMGRS(dataModel.Northing).toDouble()


        letters[0] = truncate(((dataModel.Latitude * 180 / PI) + 80) / 8).toLong()
        letters[1] = (truncate(truncate((dataModel.Latitude * 180 / PI) % 18) / 6) * 8 + truncate(dataModel.Easting / CoordinatesData.ONEHT) - 1).toLong()
        letters[2] = (truncate(dataModel.Northing / CoordinatesData.ONEHT) % 20).toLong()

        if(dataModel.Zone % 2 == 0)
            letters[2] = letters[2]!! + 5

        return MakeMGRSString(letters)
    }

    private fun RoundMGRS(value : Double) : Long{
        var ival  = value.toLong()
        val tmpValue = value - ival
        if(tmpValue > 0.5 || (tmpValue == 0.5 && ival % 2L == 1L))
            ival ++
        return  ival
    }

    private fun MakeMGRSString(letters : Array<Long?>) : String{
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

        mgrs = String.format("%2d", dataModel.Zone)
        mgrs += alphabet[CoordinatesData.LatitudeBandTable[letters[0]!!.toInt()].toInt()]
        mgrs += alphabet[CoordinatesData.RowBandSTR[letters[1]!!.toInt()].toInt()]
        mgrs += alphabet[CoordinatesData.ColBandSTR[letters[2]!!.toInt()].toInt()]

        var easting = (dataModel.Easting / 100000 - truncate(dataModel.Easting / 100000)) * 100000

        if(easting >= 99999.5)
            easting = 99999.0

        mgrs += String.format("%5d", easting.toInt())

        var northing = (dataModel.Northing / 100000 - truncate(dataModel.Northing / 100000)) * 100000

        if(northing >= 99999.5)
            northing = 99999.0

        mgrs += String.format("%5d", northing.toInt())

        return mgrs
    }
}