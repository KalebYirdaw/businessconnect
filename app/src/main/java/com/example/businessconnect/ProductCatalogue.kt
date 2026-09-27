package com.example.businessconnect

data class CatalogueProduct(
    val name: String,
    val category: String
)

object ProductCatalogue {

    val products = listOf(

        CatalogueProduct(
            name = "Coca-Cola",
            category = "Beverages"
        ),

        CatalogueProduct(
            name = "Coca-Cola Zero Sugar",
            category = "Beverages"
        ),

        CatalogueProduct(
            name = "Sprite",
            category = "Beverages"
        ),

        CatalogueProduct(
            name = "Fanta",
            category = "Beverages"
        ),

        CatalogueProduct(
            name = "Schweppes",
            category = "Beverages"
        ),

        CatalogueProduct(
            name = "Stoney",
            category = "Beverages"
        ),

        CatalogueProduct(
            name = "Powerade",
            category = "Sports Drinks"
        ),

        CatalogueProduct(
            name = "Monster",
            category = "Energy Drinks"
        ),

        CatalogueProduct(
            name = "Water",
            category = "Water"
        )
    )
}