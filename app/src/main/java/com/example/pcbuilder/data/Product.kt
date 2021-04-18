package com.example.pcbuilder.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var productCode: String = "",
    var productName: String = "",
    var productCompany: String = "",
    var productType: String = "",
    var productPrice: String = ""
): Parcelable