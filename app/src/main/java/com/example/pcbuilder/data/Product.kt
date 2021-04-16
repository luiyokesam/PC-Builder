package com.example.pcbuilder.data

import com.google.firebase.database.Exclude

data class Product (

    @get:Exclude
    var id: String? =null,
    var barcode: String? = null,
    var productName: String? = null,
    var companyName: String?= null,
    var productType: String? = null,
    var productPrice: String? = null,
    @get:Exclude
    var isDeleted: Boolean = false
){
    override fun equals(other: Any?): Boolean{
        return if(other is Product){
            other.id == id
        }else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (barcode?.hashCode() ?: 0)
        result = 31 * result + (productName?.hashCode() ?: 0)
        result = 31 * result + (companyName?.hashCode() ?: 0)
        result = 31 * result + (productType?.hashCode() ?: 0)
        result = 31 * result + (productPrice?.hashCode() ?: 0)
        result = 31 * result + isDeleted.hashCode()
        return result
    }

}