package com.example.android.medicinechest.addlistpage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.medicinechest.database.MedicineChestDatabaseDao

class ListAddPageViewModelFactory(
    private val dao: MedicineChestDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListAddPageViewModel::class.java)) {
            return ListAddPageViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

