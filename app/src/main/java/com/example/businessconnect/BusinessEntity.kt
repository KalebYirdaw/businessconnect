package com.example.businessconnect

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "businesses")
data class BusinessEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val businessName: String = "",
    val businessType: String = "",
    val registrationNumber: String = "",
    val businessPhone: String = "",
    val businessEmail: String = "",

    val ownerName: String = "",
    val contactPerson: String = "",
    val ownerPhone: String = "",
    val ownerEmail: String = "",

    val streetAddress: String = "",
    val city: String = "",
    val province: String = "",
    val postalCode: String = "",
    val latitude: String = "",
    val longitude: String = "",

    val productName: String = "",
    val productCategory: String = "",
    val productDescription: String = "",
    val price: String = "",
    val quantity: String = ""
)