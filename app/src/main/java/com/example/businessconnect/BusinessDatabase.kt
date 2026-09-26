package com.example.businessconnect

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        BusinessEntity::class,
        ProductEntity::class,
        ProductCatalogueEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class BusinessDatabase : RoomDatabase() {

    abstract fun businessDao(): BusinessDao

    abstract fun productDao(): ProductDao

    abstract fun productCatalogueDao():
            ProductCatalogueDao

    companion object {

        private val MIGRATION_1_2 =
            object : Migration(1, 2) {

                override fun migrate(
                    database: SupportSQLiteDatabase
                ) {

                    database.execSQL(
                        """
                        ALTER TABLE businesses
                        ADD COLUMN storeStatus TEXT NOT NULL DEFAULT 'New Prospect'
                        """.trimIndent()
                    )
                }
            }

        private val MIGRATION_2_3 =
            object : Migration(2, 3) {

                override fun migrate(
                    database: SupportSQLiteDatabase
                ) {

                    database.execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS products (
                            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                            businessId INTEGER NOT NULL,
                            productName TEXT NOT NULL,
                            productCategory TEXT NOT NULL,
                            availability TEXT NOT NULL,
                            quantity TEXT NOT NULL,
                            price TEXT NOT NULL,
                            notes TEXT NOT NULL,
                            FOREIGN KEY(businessId)
                                REFERENCES businesses(id)
                                ON DELETE CASCADE
                        )
                        """.trimIndent()
                    )

                    database.execSQL(
                        """
                        CREATE INDEX IF NOT EXISTS index_products_businessId
                        ON products(businessId)
                        """.trimIndent()
                    )
                }
            }

        private val MIGRATION_3_4 =
            object : Migration(3, 4) {

                override fun migrate(
                    database: SupportSQLiteDatabase
                ) {

                    database.execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS product_catalogue (
                            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                            name TEXT NOT NULL,
                            category TEXT NOT NULL
                        )
                        """.trimIndent()
                    )
                }
            }

        @Volatile
        private var INSTANCE:
                BusinessDatabase? = null

        fun getDatabase(
            context: Context
        ): BusinessDatabase {

            return INSTANCE
                ?: synchronized(this) {

                    val instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            BusinessDatabase::class.java,
                            "businessconnect_database"
                        )
                            .addMigrations(
                                MIGRATION_1_2,
                                MIGRATION_2_3,
                                MIGRATION_3_4
                            )
                            .build()

                    INSTANCE =
                        instance

                    instance
                }
        }
    }
}