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

    fun ConvertMGRSToGeodetic(mgrs : String) : LatLng{
        dataModel = ConvertingDataModel(0.0,0.0,0,'0',0.0,0.0)
        return LatLng(dataModel.Latitude, dataModel.Longitude)
    }

    private fun MGRSToUTM(mgrs : String){
        val letters = arrayOfNulls<Long>(3)
        dataModel.Zone = mgrs.substring(0,1).toInt()

        letters[0] =(mgrs[3].toInt() - 65) as Long
        letters[1] =(mgrs[4].toInt() - 65) as Long
        letters[2] =(mgrs[5].toInt() - 65) as Long

        val strMGRS = mgrs.substring(5, mgrs.lastIndex)
        val inPrecision = Math.round(strMGRS.length/2.0)
        dataModel.Easting = strMGRS.substring(0, inPrecision as Int-1).toDouble()
        dataModel.Northing = strMGRS.substring(inPrecision).toDouble()

        if(!(letters[0] == CoordinatesData.LETTER_X && (dataModel.Zone == 32 || dataModel.Zone == 34 || dataModel.Zone == 36) )){
            if(letters[0]!! < CoordinatesData.LETTER_N)
                dataModel.Hemisphere = 'S'
            else
                dataModel.Hemisphere = 'N'
        }

        val ltr2LowValue : Long
        val ltr2HighValue : Long
        val falseNorthing : Double

        when(dataModel.Zone % 6){
            1 or 4 -> {
                ltr2LowValue = CoordinatesData.LETTER_A
                ltr2HighValue = CoordinatesData.LETTER_B
            }

            2 or 5 -> {
                ltr2LowValue = CoordinatesData.LETTER_J
                ltr2HighValue = CoordinatesData.LETTER_R
            }

            else -> {
                ltr2LowValue = CoordinatesData.LETTER_S
                ltr2HighValue = CoordinatesData.LETTER_Z
            }
        }

        falseNorthing = if(dataModel.Zone % 2 == 0)
            500000.0
        else
            1000000.00

        var gridEasting  = 0.0
        var gridNorthing = 0.0

        if(!(letters[1]!! < ltr2LowValue || letters[2]!! > ltr2HighValue || letters[2]!! > CoordinatesData.LETTER_V)){
            gridNorthing = letters[2] as Int * CoordinatesData.ONEHT + falseNorthing
            gridEasting = (letters[1] as Int - ltr2LowValue + 1) * CoordinatesData.ONEHT
            if(ltr2LowValue == CoordinatesData.LETTER_J && letters[1]!! > CoordinatesData.LETTER_O){
                gridEasting -= CoordinatesData.ONEHT
            }
            if(letters[2]!! > CoordinatesData.LETTER_O){
                gridNorthing -= CoordinatesData.ONEHT
            }
            if(letters[2]!! > CoordinatesData.LETTER_I){
                gridNorthing -= CoordinatesData.ONEHT
            }
            if(gridNorthing >= CoordinatesData.TWOMIL){
                gridNorthing -= CoordinatesData.TWOMIL
            }
        }
        var minNorthing = GetLatitudeBandMinNorthing(letters[0] as Int)
        val minNorthing2 = minNorthing
        if(minNorthing != null){
            while (minNorthing >= CoordinatesData.TWOMIL)
                minNorthing -= CoordinatesData.TWOMIL
            gridNorthing -= minNorthing

            if(gridNorthing < 0.0)
                gridNorthing += CoordinatesData.TWOMIL
            gridNorthing += minNorthing2!!

            dataModel.Easting += gridEasting
            dataModel.Northing += gridNorthing
        }

        UTM = UTM(dataModel, CoordinatesData.MGRS_a, CoordinatesData.MGRS_f)
    }

    private fun GetLatitudeBandMinNorthing(letter : Int) : Double?{
        when(letter){
            in CoordinatesData.LETTER_C .. CoordinatesData.LETTER_H ->{
                return CoordinatesData.LatitudeBandSTR2[letter-2]
            }
            in CoordinatesData.LETTER_J .. CoordinatesData.LETTER_N ->{
                return CoordinatesData.LatitudeBandSTR2[letter-3]
            }
            in CoordinatesData.LETTER_P .. CoordinatesData.LETTER_X->{
                return  CoordinatesData.LatitudeBandSTR2[letter-4]
            }
        }
        return null
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