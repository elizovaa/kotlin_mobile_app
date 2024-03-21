package com.example.android.medicinechest.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "list_table")
data class Inventory (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_id")
    var listId: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String = "",
)