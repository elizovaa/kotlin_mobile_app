package com.example.android.medicinechest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class, Inventory::class, InventoryProductCrossRef::class], version = 1, exportSchema = false)
abstract class MedicineChestDatabase : RoomDatabase() {
    abstract fun getMedicineChestDatabaseDao(): MedicineChestDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MedicineChestDatabase? = null

        fun getInstance(context: Context): MedicineChestDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        MedicineChestDatabase::class.java, "medicine_chest_db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}