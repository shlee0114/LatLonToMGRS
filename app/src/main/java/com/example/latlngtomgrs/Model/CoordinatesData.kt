package com.example.latlngtomgrs.Model

import kotlin.math.PI


class CoordinatesData {
companion object {
    val DEG_TO_RAD = 0.017453292519943295
    val RAD_TO_DEG = 57.29577951308232087

    val LETTER_A = 0L
    val LETTER_B = 1L
    val LETTER_C = 2L
    val LETTER_D = 3L
    val LETTER_E = 4L
    val LETTER_F = 5L
    val LETTER_G = 6L
    val LETTER_H = 7L
    val LETTER_J = 9L
    val LETTER_K = 10L
    val LETTER_L = 11L
    val LETTER_M = 12L
    val LETTER_N = 13L
    val LETTER_P = 15L
    val LETTER_Q = 16L
    val LETTER_R = 17L
    val LETTER_S = 18L
    val LETTER_T = 19L
    val LETTER_U = 20L
    val LETTER_V = 21L
    val LETTER_W = 22L
    val LETTER_X = 23L
    val LETTER_Y = 24L
    val LETTER_Z = 25L

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

    val MAX_LAT = ((PI * 89.99) / 180.0)
    val MAX_DELTA_LONG = ((PI * 90) / 180.0)

    val LatitudeBandTable = arrayListOf(
        Latitude_Band(LETTER_C, 1100000.0, -72.0, -80.5),
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

    val UPSConstantTable = arrayListOf(
        UPS_Constant(LETTER_A, LETTER_J, LETTER_Z, LETTER_Z, 800000.0, 800000.0),
        UPS_Constant(LETTER_B, LETTER_A, LETTER_R, LETTER_Z, 2000000.0, 800000.0),
        UPS_Constant(LETTER_Y, LETTER_J, LETTER_Z, LETTER_P, 800000.0, 1300000.0),
        UPS_Constant(LETTER_Z, LETTER_A, LETTER_J, LETTER_P, 2000000.0, 1300000.0)
    )

    val RowBandSTR = arrayListOf(
        LETTER_A, LETTER_B, LETTER_C, LETTER_D, LETTER_E,
        LETTER_F, LETTER_G, LETTER_H, LETTER_J, LETTER_K,
        LETTER_L, LETTER_M, LETTER_N, LETTER_P, LETTER_Q,
        LETTER_R, LETTER_S, LETTER_T, LETTER_U, LETTER_V,
        LETTER_W, LETTER_X, LETTER_Y, LETTER_Z
    )

    val ColBandSTR = arrayListOf(
        LETTER_A, LETTER_B, LETTER_C, LETTER_D, LETTER_E,
        LETTER_F, LETTER_G, LETTER_H, LETTER_J, LETTER_K,
        LETTER_L, LETTER_M, LETTER_N, LETTER_P, LETTER_Q,
        LETTER_R, LETTER_S, LETTER_T, LETTER_U, LETTER_V,
        LETTER_A, LETTER_B, LETTER_C, LETTER_D, LETTER_E
    )
}
}