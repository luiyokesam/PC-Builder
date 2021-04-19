package com.example.pcbuilder.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StockOut (
    var productCode: String = "",
    var outQuantity: String = "",
    var outDate: String = ""
): Parcelable