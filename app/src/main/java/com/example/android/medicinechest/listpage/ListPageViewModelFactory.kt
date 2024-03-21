package com.example.android.medicinechest.listpage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.medicinechest.database.MedicineChestDatabaseDao

class ListPageViewModelFactory(
    private val listId: Long,
    private val dao: MedicineChestDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListPageViewModel::class.java)) {
            return ListPageViewModel(listId, dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

