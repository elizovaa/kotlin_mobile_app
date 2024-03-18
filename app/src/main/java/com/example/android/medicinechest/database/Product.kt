package com.example.android.medicinechest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "type")
    val type: String = "",

    @ColumnInfo(name = "amount")
    val amount: Int = 0,

    @ColumnInfo(name = "dosage")
    val dosage: String = "",

    @ColumnInfo(name = "comment")
    val comment: String = ""
)