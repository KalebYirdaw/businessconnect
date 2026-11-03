package com.example.businessconnect

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = BusinessEntity::class,
            parentColumns = ["id"],
            childColumns = ["businessId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["businessId"])
    ]
)
data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val businessId: Int,

    val productName: String = "",

    val productCategory: String = "",

    val availability: String = "Present",

    val quantity: String = "",

    val price: String = "",

    val notes: String = ""
)