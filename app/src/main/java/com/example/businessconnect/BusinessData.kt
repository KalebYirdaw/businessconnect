package com.example.businessconnect

data class BusinessData(
    var businessName: String = "",
    var businessType: String = "",
    var registrationNumber: String = "",
    var businessPhone: String = "",
    var businessEmail: String = "",

    var ownerName: String = "",
    var contactPerson: String = "",
    var ownerPhone: String = "",
    var ownerEmail: String = "",

    var streetAddress: String = "",
    var city: String = "",
    var province: String = "",
    var postalCode: String = "",
    var latitude: String = "",
    var longitude: String = "",

    var storeStatus: String = "New Prospect",

    var productName: String = "",
    var productCategory: String = "",
    var productDescription: String = "",
    var price: String = "",
    var quantity: String = "",

    var products: List<ProductAssessment> = emptyList()
)