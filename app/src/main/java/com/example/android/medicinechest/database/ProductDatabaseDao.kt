package com.example.android.medicinechest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDatabaseDao {
    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Query("SELECT * FROM product_table WHERE id = :key")
    fun get(key: Long): Product?

    @Query("SELECT * FROM product_table WHERE name = :key")
    fun getName(key: String): Product?

    @Query("DELETE FROM product_table")
    fun clear()

    @Query("SELECT * FROM product_table ORDER BY id DESC")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert
    fun insertAll(articleList : List<Product>) : List<Long>
}