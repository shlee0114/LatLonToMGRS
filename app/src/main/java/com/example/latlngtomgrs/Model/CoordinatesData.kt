package com.example.latlngtomgrs.Model

import kotlin.math.PI

class CoordinatesData {
    val DEG_TO_RAD = 0.017453292519943295
    val RAD_TO_DEG =  57.29577951308232087

    val LETTER_A = 0
    val LETTER_B = 1
    val LETTER_C = 2
    val LETTER_D = 3
    val LETTER_E = 4
    val LETTER_F = 5
    val LETTER_G = 6
    val LETTER_H = 7
    val LETTER_J = 9
    val LETTER_K = 10
    val LETTER_L = 11
    val LETTER_M = 12
    val LETTER_N = 13
    val LETTER_P = 15
    val LETTER_Q = 16
    val LETTER_R = 17
    val LETTER_S = 18
    val LETTER_T = 19
    val LETTER_U = 20
    val LETTER_V = 21
    val LETTER_W = 22
    val LETTER_X = 23
    val LETTER_Y = 24
    val LETTER_Z = 25

    val MGRS_LETTERS = 3

    val ONEHT = 100000.0e0
    val TWOMIL = 2000000.0e0

    val PI_OVER_2 = (PI / 2)
    val MAX_PRECISION = 5
    val MIN_EASTING = 100000
    val MAX_EASTING = 900000
    val MIN_NORTHING = 0
    val MAX_NORTHING = 10000000

    val MIN_UTM_LAT = ((-80 * PI) / 180.0)
    val MAX_UTM_LAT = ((84 * PI) / 180.0)

    val MGRS_a = 6378137.0
    val MGRS_f = 1 / 298.257223563

    val MGRS_Ellipsoid_Code = "WE "

    val CLARKE_1866 = "CC"
    val CLARKE_1880 = "CD"
    val BESSEL_1841 = "BR"
    val BESSEL_1841_NAMIBIA = "BN"

    val MAX_LAT = ((PI * 89.99)/180.0)
    val MAX_DELTA_LONG = ((PI * 90)/180.0)

    val TranMerc_a = 6378137.0
    val TranMerc_f = 1 / 298.257223563
    val TranMerc_es = 0.0066943799901413800
    val TranMerc_ebs = 0.0067394967565869

    val TranMerc_Origin_Lat = 0.0
    val TranMerc_Origin_Long = 0.0
    val TranMerc_False_Northing = 0.0
    val TranMerc_False_Easting = 0.0
    val TranMerc_Scale_Factor = 1.0

    val TranMerc_ap = 6367449.1458008
    val TranMerc_bp = 16038.508696861
    val TranMerc_cp = 16.832613334334
    val TranMerc_dp = 0.021984404273757
    val TranMerc_ep = 3.1148371319283e-005

    val TranMerc_Delta_Easting = 40000000.0
    val TranMerc_Delta_Northing = 40000000.0

    val Latitude_Band_Table = arrayListOf(Latitude_Band(LETTER_C, 1100000.0, -72.0, -80.5),
        Latitude_Band(LETTER_D, 2000000.0, -64.0, -72.0),
        Latitude_Band(LETTER_E, 2800000.0, -56.0, -64.0),
        Latitude_Band(LETTER_F, 3700000.0, -48.0, -56.0),
        Latitude_Band(LETTER_G, 4600000.0, -40.0, -48.0),
        Latitude_Band(LETTER_H, 5500000.0, -32.0, -40.0),
        Latitude_Band(LETTER_J, 6400000.0, -24.0, -32.0),
        Latitude_Band(LETTER_K, 7300000.0, -16.0, -24.0),
        Latitude_Band(LETTER_L, 8200000.0, -8.0, -16.0),
        Latitude_Band(LETTER_M, 9100000.0, 0.0, -8.0),
        Latitude_Band(LETTER_N, 0.0, 8.0, 0.0),
        Latitude_Band(LETTER_P, 800000.0, 16.0, 8.0),
        Latitude_Band(LETTER_Q, 1700000.0, 24.0, 16.0),
        Latitude_Band(LETTER_R, 2600000.0, 32.0, 24.0),
        Latitude_Band(LETTER_S, 3500000.0, 40.0, 32.0),
        Latitude_Band(LETTER_T, 4400000.0, 48.0, 40.0),
        Latitude_Band(LETTER_U, 5300000.0, 56.0, 48.0),
        Latitude_Band(LETTER_V, 6200000.0, 64.0, 56.0),
        Latitude_Band(LETTER_W, 7000000.0, 72.0, 64.0),
        Latitude_Band(LETTER_X, 7900000.0, 84.5, 72.0)
        )

    val UPS_Constant_Table = arrayListOf( UPS_Constant(LETTER_A, LETTER_J, LETTER_Z, LETTER_Z, 800000.0, 800000.0),
        UPS_Constant(LETTER_B, LETTER_A, LETTER_R, LETTER_Z, 2000000.0, 800000.0),
        UPS_Constant(LETTER_Y, LETTER_J, LETTER_Z, LETTER_P, 800000.0, 1300000.0),
        UPS_Constant(LETTER_Z, LETTER_A, LETTER_J, LETTER_P, 2000000.0, 1300000.0)
    )

     val Row_BandSTR = arrayListOf(LETTER_A,LETTER_B,LETTER_C,LETTER_D,LETTER_E,
         LETTER_F,LETTER_G,LETTER_H,LETTER_J,LETTER_K,
         LETTER_L,LETTER_M,LETTER_N,LETTER_P,LETTER_Q,
         LETTER_R,LETTER_S,LETTER_T,LETTER_U,LETTER_V,
         LETTER_W,LETTER_X,LETTER_Y,LETTER_Z)

    val Col_BandSTR = arrayListOf(LETTER_A,LETTER_B,LETTER_C,LETTER_D,LETTER_E,
        LETTER_F,LETTER_G,LETTER_H,LETTER_J,LETTER_K,
        LETTER_L,LETTER_M,LETTER_N,LETTER_P,LETTER_Q,
        LETTER_R,LETTER_S,LETTER_T,LETTER_U,LETTER_V,
        LETTER_A,LETTER_B,LETTER_C,LETTER_D,LETTER_E)
}