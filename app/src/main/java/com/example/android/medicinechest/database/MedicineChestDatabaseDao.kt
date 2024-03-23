package com.example.android.medicinechest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MedicineChestDatabaseDao {
    @Insert
    fun insert(product: Product): Long

    @Insert
    fun insert(inventory: Inventory): Long

    @Insert
    fun insert(inventoryProductCrossRef: InventoryProductCrossRef): Long

    @Update
    fun update(product: Product)

    @Query("DELETE FROM product_table WHERE product_id = :key")
    fun delete(key: Long)

    @Query("SELECT * FROM product_table WHERE product_id = :key")
    fun get(key: Long): Product?

    @Query("SELECT * FROM list_table WHERE list_id = :key")
    fun getList(key: Long): Inventory?

    @Query("SELECT * FROM product_list_table WHERE product_id = :key")
    fun getRef(key: Long): InventoryProductCrossRef?

    @Query("SELECT * FROM product_table WHERE name = :key")
    fun getName(key: String): Product?

    @Query("DELETE FROM product_table")
    fun clear()

    @Query("SELECT * FROM product_table ORDER BY product_id DESC")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM list_table ORDER BY list_id DESC")
    fun getAllLists(): LiveData<List<Inventory>>

    @Query(
        "SELECT product_table.product_id, product_table.name, product_table.type, product_table.amount, product_table.dosage, product_table.comment FROM product_table " +
                "INNER JOIN product_list_table ON product_table.product_id = product_list_table.product_id " +
                "INNER JOIN list_table ON product_list_table.list_id = list_table.list_id " +
                "WHERE list_table.list_id = :listId"
    )
    fun getProductsOfList(listId: Long): LiveData<List<Product>>
}