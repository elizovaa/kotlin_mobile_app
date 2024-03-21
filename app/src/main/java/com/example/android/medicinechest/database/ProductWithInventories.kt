package com.example.android.medicinechest.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ProductWithInventories(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "list_id",
        associateBy = Junction(InventoryProductCrossRef::class)
    )
    val lists: List<Inventory>
)