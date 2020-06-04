package com.example.latlngtomgrs.Model

import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class ConvertDataModelTrammerc ( var TranMerc_a : Double, var TranMerc_f : Double, var OriginLatitude : Double, var CentralMerdian : Double, var FalseEasting : Double, var FalseNorthing : Double, var ScaleFactor : Double ){
    var TranMercOriginLat = 0.0
    var TranMercOriginLong = 0.0
    var TranMercFalseNorthing = 0.0
    var TranMercFalseEasting = 0.0
    var TranMercScaleFactor = 1.0

    var TranMercEs = 2 * TranMerc_f - TranMerc_f * TranMerc_f
    var TranMercEbs = (1/(1-TranMercEs)) - 1
    var TranMerc_b = TranMerc_a * (1-TranMerc_f)

    var tn = (TranMerc_a - TranMerc_b) / (TranMerc_a + TranMerc_b)
    var tn2 = tn*tn
    var tn3 = tn2 * tn
    var tn4 = tn3 * tn
    var tn5 = tn4 * tn

    var dummyNorthing = 0.0

    var TranMercAp = TranMerc_a * (1.0e0 - tn + 5.0e0 * (tn2 - tn3) / 4.0e0
            + 81.0e0 * (tn4 - tn5) / 64.0e0)
    var TranMercBp = 3.0e0 * TranMerc_a * (tn - tn2 + 7.0e0 * (tn3 - tn4)
            / 8.0e0 + 55.0e0 * tn5 / 64.0e0) / 2.0e0
    var TranMercCp = 15.0e0 * TranMerc_a * (tn2 - tn3 + 3.0e0 * (tn4 - tn5) / 4.0e0) / 16.0
    var TranMercDp = 35.0e0 * TranMerc_a * (tn3 - tn4 + 11.0e0 * tn5 / 16.0e0) / 48.0e0
    var TranMercEp = 315.0e0 * TranMerc_a * (tn4 - tn5) / 512.0e0

    var TranmercDeltaEasting = 40000000.0
    var TranMercDeltaNorthing = 40000000.0

    fun SPHTMD(Latitude : Double) : Double
            = TranMercAp * Latitude - TranMercBp * sin(2 * Latitude) + TranMercCp * sin(4 * Latitude) + TranMercDp * (6*Latitude) + TranMercEp * (sin(8 * Latitude))

    fun SPHSM(Latitude: Double) : Double
            = TranMerc_a / sqrt(1 - TranMercEs * sin(Latitude).pow(2))

    fun SPHSR(Latitude: Double) : Double
            = TranMerc_a * (1 - TranMercEs) / (sqrt(1 - TranMercEs * sin(Latitude).pow(2))).pow(3)
}