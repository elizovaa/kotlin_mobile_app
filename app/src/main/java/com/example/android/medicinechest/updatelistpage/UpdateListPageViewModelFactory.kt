package com.example.android.medicinechest.updatelistpage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.medicinechest.database.MedicineChestDatabaseDao

class UpdateListPageViewModelFactory(
    private val listId: Long,
    private val dao: MedicineChestDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateListPageViewModel::class.java)) {
            return UpdateListPageViewModel(listId, dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

