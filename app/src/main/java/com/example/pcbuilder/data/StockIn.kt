package com.example.pcbuilder.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StockIn (
    var productCode: String = "",
    var inQuantity: String = "",
    var inDate: String = ""
): Parcelable