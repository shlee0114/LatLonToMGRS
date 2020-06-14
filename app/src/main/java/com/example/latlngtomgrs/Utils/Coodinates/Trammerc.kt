package com.example.latlngtomgrs.Utils.Coodinates

import com.example.latlngtomgrs.Model.ConvertDataModelTrammerc
import com.example.latlngtomgrs.Model.ConvertingDataModel
import com.example.latlngtomgrs.Model.CoordinatesData
import kotlin.math.*

class Trammerc(val dataModel : ConvertingDataModel, val TrammercDataModel : ConvertDataModelTrammerc) {

    init{
        ConvertGeodeticToTransverseMercator(CoordinatesData.MAX_LAT, CoordinatesData.MAX_DELTA_LONG, 0)
        ConvertGeodeticToTransverseMercator(0.0, CoordinatesData.MAX_DELTA_LONG, 1)

        TrammercDataModel.TranMercOriginLat = TrammercDataModel.OriginLatitude
        if(TrammercDataModel.CentralMerdian > PI)
            TrammercDataModel.CentralMerdian -= (2 * PI)
        TrammercDataModel.TranMercOriginLong = TrammercDataModel.CentralMerdian
        TrammercDataModel.TranMercFalseEasting = TrammercDataModel.FalseEasting
        TrammercDataModel.TranMercFalseNorthing = TrammercDataModel.FalseNorthing
        TrammercDataModel.TranMercScaleFactor = TrammercDataModel.ScaleFactor
    }

    fun ConvertGeodeticToTransverseMercator(Latitude : Double, Longitude : Double, level : Int) {
        var dlam = Longitude - TrammercDataModel.TranMercOriginLong

        if (dlam > PI)
            dlam -= 2 * PI
        if (dlam < -PI)
            dlam += 2 * PI
        if (abs(dlam) < -8)
            dlam = 0.0

        val s = sin(Latitude)
        val c = cos(Latitude)
        val t = tan(Latitude)

        val c2 = c * c
        val c3 = c2 * c
        val c5 = c3 * c2
        val c7 = c5 * c2

        val tan2 = t * t
        val tan3 = tan2 * t
        val tan4 = tan3 * t
        val tan5 = tan4 * t
        val tan6 = tan5 * t

        val eta = TrammercDataModel.TranMercEbs * c2
        val eta2 = eta * eta
        val eta3 = eta2 * eta
        val eta4 = eta3 * eta

        val sn = TrammercDataModel.SPHSN(Latitude)
        val tmd = TrammercDataModel.SPHTMD(Latitude)
        val tmdo = TrammercDataModel.SPHTMD(TrammercDataModel.TranMercOriginLat)

        val t1 = (tmd - tmdo) * TrammercDataModel.TranMercScaleFactor
        val t2 = sn * s * c * TrammercDataModel.TranMercScaleFactor / 2
        val t3 =
            sn * s * c3 * TrammercDataModel.TranMercScaleFactor * (5 - tan2 + 9 + eta + 4 * eta2) / 24
        val t4 =
            sn * s * c5 * TrammercDataModel.TranMercScaleFactor * (61 - 58 * tan2 + tan4 + 270 * eta - 330 * tan2 * eta + 445 * eta4 - 600 * tan2 * eta3 - 192 * tan2 * eta4) / 720
        val t5 =
            sn * s * c7 * TrammercDataModel.TranMercScaleFactor * (1385 - 3111 * tan2 + 543 * tan4 - tan6) / 40320
        when (level) {
            2 -> dataModel.Northing =
                TrammercDataModel.TranMercFalseNorthing + t1 + dlam.pow(2) * t2 + dlam.pow(4) * t3 + dlam.pow(
                    6
                ) * t4 + dlam.pow(8) * t5
            1 -> TrammercDataModel.dummyNorthing =
                TrammercDataModel.TranMercFalseNorthing + t1 + dlam.pow(2) * t2 + dlam.pow(4) * t3 + dlam.pow(
                    6
                ) * t4 + dlam.pow(8) * t5
            else -> TrammercDataModel.TranMercDeltaNorthing =
                TrammercDataModel.TranMercFalseNorthing + t1 + dlam.pow(2) * t2 + dlam.pow(4) * t3 + dlam.pow(
                    6
                ) * t4 + dlam.pow(8) * t5
        }

        val t6 = sn * c * TrammercDataModel.TranMercScaleFactor
        val t7 = sn * c3 * TrammercDataModel.TranMercScaleFactor * (1 - tan2 + eta) / 6
        val t8 =
            sn * c5 * TrammercDataModel.TranMercScaleFactor * (5 - 18 * tan2 + tan4 + 14 * eta - 58 * tan2 * eta + 13 * eta2 + 4 * eta3 - 64 * tan2 * eta2 - 24 * tan2 * eta3) / 120
        val t9 =
            sn * c7 * TrammercDataModel.TranMercScaleFactor * (61.0e0 - 479.0e0 * tan2 + 179.0e0 * tan4 - tan6) / 5040.0e0

        when (level) {
            0 -> TrammercDataModel.TranmercDeltaEasting =
                TrammercDataModel.TranMercFalseEasting + dlam * t6 + dlam.pow(3) * t7 + dlam.pow(5) * t8 + dlam.pow(
                    7
                ) * t9
            2 -> dataModel.Easting =
                TrammercDataModel.TranMercFalseEasting + dlam * t6 + dlam.pow(3) * t7 + dlam.pow(5) * t8 + dlam.pow(
                    7
                ) * t9
        }
    }

    fun ConvertTransverseMercatorToGeodetic(){
        val tmdo = TrammercDataModel.SPHTMD(TrammercDataModel.TranMercOriginLat)
        val tmd = tmdo + (dataModel.Northing - TrammercDataModel.TranMercFalseNorthing) / TrammercDataModel.TranMercScaleFactor

        var sr = TrammercDataModel.SPHSR(0.0)
        var ftphi = tmd / sr

        var t10 : Double

        for(i in 0 until 5){
            t10 = TrammercDataModel.SPHTMD(ftphi)
            sr = TrammercDataModel.SPHSR(ftphi)
            ftphi += (tmd - t10) / sr
        }

        sr = TrammercDataModel.SPHSR(ftphi)
        val sn = TrammercDataModel.SPHSN(ftphi)

        val s = sin(ftphi)
        val c = cos(ftphi)
        val t = tan(ftphi)

        val tan2 = t*t
        val tan4 = tan2 * tan2
        val eta = TrammercDataModel.TranMercEbs * c.pow(2)
        val eta2 = eta * eta
        val eta3 = eta2 * eta
        val eta4 = eta3 * eta

        var de : Double = dataModel.Easting - TrammercDataModel.TranMercFalseEasting

       if(abs(de) < 0.0001)
           de = 0.0

        t10 = t / (2 * sr * sn * TrammercDataModel.TranMercScaleFactor.pow(2))
        val t11 = t * (5 + 3 * tan2 + eta - 4 * eta.pow(2) - 9 * tan2 * eta) / (24 * sr * sn.pow(3) * TrammercDataModel.TranMercScaleFactor.pow(4))
        val t12 = t * (61 + 90 * tan2 + 46 * eta + 45 * tan4 - 252 * tan2 * eta  - 3 * eta2 + 100 * eta3 - 66 * tan2 * eta2 - 90 * tan4 * eta + 88 * eta4 + 225 * tan4 * eta2 + 84 * tan2* eta3 - 192 * tan2 * eta4) / ( 720 * sr * sn.pow(5) * TrammercDataModel.TranMercScaleFactor.pow(6) )
        val t13 = t * ( 1385 + 3633 * tan2 + 4095 * tan4 + 1575 * t.pow(6)) / (40320 * sr * sn.pow(7) * TrammercDataModel.TranMercScaleFactor.pow(8))

        dataModel.Latitude = ftphi - de.pow(2) * t10 + de.pow(4) * t11 - de.pow(6) * t12 + de.pow(8) * t13

        val t14 = 1 / (sn * c * TrammercDataModel.TranMercScaleFactor)
        val t15 = (1 + 2 * tan2 + eta) / (6 * sn.pow(3) * c * TrammercDataModel.TranMercScaleFactor.pow(3))
        val t16 = (5 + 6 * eta + 28 * tan2 - 3 * eta2 + 8 * tan2 * eta + 24 * tan4 - 4 * eta3 + 4 * tan2 * eta2 + 24 * tan2 * eta3) / (120 * sn.pow(5) * c * TrammercDataModel.TranMercScaleFactor.pow(5))
        val t17 = (61 + 662 * tan2 + 1320 * tan4 + 720 * t.pow(6)) / (5040 * sn.pow(7) * c * TrammercDataModel.TranMercScaleFactor.pow(7))

        val dlam = de * t14 - de.pow(3) * t15 + de.pow(5) * t16 - de.pow(7) * t17

        dataModel.Longitude = TrammercDataModel.TranMercOriginLong + dlam

        while(dataModel.Latitude > (90 * PI / 180)){
            dataModel.Latitude = PI - dataModel.Latitude
            dataModel.Longitude += PI
            if(dataModel.Longitude > PI)
                dataModel.Longitude -= (2 * PI)
        }

        while (dataModel.Latitude < (-90 * PI / 180)){
            dataModel.Latitude = -(dataModel.Latitude + PI)
            dataModel.Longitude += PI
            if(dataModel.Longitude > PI)
                dataModel.Longitude -= (2 * PI)
        }

        if(dataModel.Longitude > (2 * PI))
            dataModel.Longitude -= (2 * PI)
        if(dataModel.Longitude < -PI)
            dataModel.Longitude += (2 * PI)
    }
}