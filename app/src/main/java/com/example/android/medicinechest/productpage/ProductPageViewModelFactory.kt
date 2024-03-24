package com.example.android.medicinechest.productpage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.medicinechest.database.MedicineChestDatabaseDao

class ProductPageViewModelFactory(
    private val id: Long,
    private val dao: MedicineChestDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductPageViewModel::class.java)) {
            return ProductPageViewModel(id, dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

