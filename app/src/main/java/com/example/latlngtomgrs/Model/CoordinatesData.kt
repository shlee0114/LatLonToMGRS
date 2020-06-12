package com.example.latlngtomgrs.Model

import kotlin.math.PI


class CoordinatesData {
companion object {

    val LETTER_A = 0L
    val LETTER_B = 1L
    val LETTER_C = 2L
    val LETTER_D = 3L
    val LETTER_E = 4L
    val LETTER_F = 5L
    val LETTER_G = 6L
    val LETTER_H = 7L
    val LETTER_I = 8L
    val LETTER_J = 9L
    val LETTER_K = 10L
    val LETTER_L = 11L
    val LETTER_M = 12L
    val LETTER_N = 13L
    val LETTER_O = 14L
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

    val ONEHT = 100000.0e0
    val TWOMIL = 2000000.0e0

    val PI_OVER_2 = (PI / 2)

    val MGRS_a = 6378137.0
    val MGRS_f = 1 / 298.257223563
    val MAX_LAT = ((PI * 89.99) / 180.0)
    val MAX_DELTA_LONG = ((PI * 90) / 180.0)

    val LatitudeBandTable = arrayListOf(
        LETTER_C,
        LETTER_D,
        LETTER_E,
        LETTER_F,
        LETTER_G,
        LETTER_H,
        LETTER_J,
        LETTER_K,
        LETTER_L,
        LETTER_M,
        LETTER_N,
        LETTER_P,
        LETTER_Q,
        LETTER_R,
        LETTER_S,
        LETTER_T,
        LETTER_U,
        LETTER_V,
        LETTER_W,
        LETTER_X
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
    val LatitudeBandSTR2 = arrayListOf (
    1100000.0,2000000.0,2800000.0,3700000.0,4600000.0,5500000.0,6400000.0,7300000.0,
    8200000.0,9100000.0,0.0,800000.0,1700000.0,2600000.0,3500000.0,4400000.0,5300000.0,6200000.0,7000000.0,7900000.0)

}
}