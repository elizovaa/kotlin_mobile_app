package com.example.android.medicinechest.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class InventoryWithProducts(
    @Embedded val list: Inventory,
    @Relation(
        parentColumn = "list_id",
        entityColumn = "product_id",
        associateBy = Junction(InventoryProductCrossRef::class)
    )
    val products: List<Product>
)