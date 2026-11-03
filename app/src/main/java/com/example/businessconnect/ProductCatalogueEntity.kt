package com.example.businessconnect

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_catalogue"
)
data class ProductCatalogueEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String = "",

    val category: String = ""
)