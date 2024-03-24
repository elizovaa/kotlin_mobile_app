package com.example.android.medicinechest

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.medicinechest.database.MedicineChestDatabase
import com.example.android.medicinechest.database.MedicineChestDatabaseDao
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
        db = Room.inMemoryDatabaseBuilder(context, MedicineChestDatabase::class.java)
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
    }
}

