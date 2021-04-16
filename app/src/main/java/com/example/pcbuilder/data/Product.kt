package com.example.pcbuilder.data

import com.google.firebase.database.Exclude

data class Product (

    @get:Exclude
    var id: String? =null,
    var barcode: String? = null,
    var productName: String? = null,
    var companyName: String?= null,
    var productType: String? = null,
    var productPrice: String? = null
){

}