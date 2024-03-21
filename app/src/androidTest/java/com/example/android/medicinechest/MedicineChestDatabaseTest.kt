package com.example.android.medicinechest

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.medicinechest.database.Inventory
import com.example.android.medicinechest.database.InventoryProductCrossRef
import com.example.android.medicinechest.database.MedicineChestDatabase
import com.example.android.medicinechest.database.MedicineChestDatabaseDao
import com.example.android.medicinechest.database.Product
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MedicineChestDatabaseTest {

    private lateinit var dao: MedicineChestDatabaseDao
    private lateinit var db: MedicineChestDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, MedicineChestDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        dao = db.getMedicineChestDatabaseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val product = Product(name = "my_product")
        val idProduct = dao.insert(product)
        val list = Inventory(name = "my_list")
        val idList = dao.insert(list)
        val refs = InventoryProductCrossRef(listId = idList, productId = idProduct)
        dao.insert(refs)
        val products = dao.getProductsOfList("my_list")
        assertEquals(products != null, true)
        assertEquals("my_product", products.get(0).name)
        println()
//        assertEquals(products.get(0).name, "my_product")
    }
}

