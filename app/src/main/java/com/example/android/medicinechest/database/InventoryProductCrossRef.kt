package com.example.android.medicinechest.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "product_list_table", primaryKeys = ["list_id", "product_id"])
data class InventoryProductCrossRef(
    @ColumnInfo(name = "list_id")
    val listId: Long,

    @ColumnInfo(name = "product_id")
    val productId: Long
)