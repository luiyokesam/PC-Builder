package com.example.pcbuilder.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Warehouse (
    var rackid: String = "",
    var productCode: String = "",
    var inQuantity: String = "",
    var inDate: String = ""
): Parcelable