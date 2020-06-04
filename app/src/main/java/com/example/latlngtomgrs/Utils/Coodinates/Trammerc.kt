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

    fun ConvertGeodeticToTransverseMercator(Latitude : Double, Longitude : Double, level : Int){
        var dlam = Longitude - TrammercDataModel.TranMercOriginLong

        if(dlam > PI)
            dlam -= 2 * PI
        if(dlam < -PI)
            dlam += 2 * PI
        if(abs(dlam) < -8)
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

        val sn = TrammercDataModel.SPHSM(Latitude)
        val tmd = TrammercDataModel.SPHTMD(Latitude)
        val tmdo = TrammercDataModel.SPHTMD(TrammercDataModel.TranMercOriginLat)

        val t1 = (tmd - tmdo) * TrammercDataModel.TranMercScaleFactor
        val t2 = sn * s * c * TrammercDataModel.TranMercScaleFactor / 2
        val t3 = sn * s * c3 * TrammercDataModel.TranMercScaleFactor * (5-tan2 + 9 + eta + 4 *eta2) / 24
        val t4 = sn * s * c5 * TrammercDataModel.TranMercScaleFactor * (61 - 58 * tan2 + tan4 + 270 * eta - 330 * tan2 * eta + 445 * eta4 - 600 * tan2 * eta3 - 192 * tan2 * eta4) / 720
        val t5 = sn * s * c7 * TrammercDataModel.TranMercScaleFactor * (1385 - 3111 * tan2 + 543 * tan4 - tan6) / 40320
        if(level == 2)
            dataModel.Northing = TrammercDataModel.TranMercFalseNorthing + t1 + dlam.pow(2) * t2 + dlam.pow(4) * t3 + dlam.pow(6) * t4 + dlam.pow(8) * t5
        else if(level == 1)
            TrammercDataModel.dummyNorthing = TrammercDataModel.TranMercFalseNorthing + t1 + dlam.pow(2) * t2 + dlam.pow(4) * t3 + dlam.pow(6) * t4 + dlam.pow(8) * t5
        else
        TrammercDataModel.TranMercDeltaNorthing =  TrammercDataModel.TranMercFalseNorthing + t1 + dlam.pow(2) * t2 + dlam.pow(4) * t3 + dlam.pow(6) * t4 + dlam.pow(8) * t5

        val t6 = sn * c * TrammercDataModel.TranMercScaleFactor
        val t7 = sn * c3 * TrammercDataModel.TranMercScaleFactor * (1 - tan2 + eta) / 6
        val t8 = sn * c5 * TrammercDataModel.TranMercScaleFactor  * (5 - 18 * tan2 + tan4 + 14 * eta - 58 * tan2 * eta + 13 * eta2 + 4 * eta3 - 64 * tan2 * eta2 - 24 * tan2 * eta3) / 120
        val t9 = sn * c7 * TrammercDataModel.TranMercScaleFactor * (61.0e0 - 479.0e0 * tan2 + 179.0e0 * tan4 - tan6) / 5040.0e0
        if(level == 0)
            TrammercDataModel.TranmercDeltaEasting = TrammercDataModel.TranMercFalseEasting + dlam * t6 + dlam.pow(3) * t7 + dlam.pow(5) * t8 + dlam.pow(7) * t9
        else if(level == 2)
            dataModel.Easting = TrammercDataModel.TranMercFalseEasting + dlam * t6 + dlam.pow(3) * t7 + dlam.pow(5) * t8 + dlam.pow(7) * t9
    }
}