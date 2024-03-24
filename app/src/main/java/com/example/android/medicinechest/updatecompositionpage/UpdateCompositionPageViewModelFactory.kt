package com.example.android.medicinechest.updatecompositionpage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.medicinechest.database.MedicineChestDatabaseDao

class UpdateCompositionPageViewModelFactory(
    private val isUpdateList: Boolean,
    private val listId: Long,
    private val dao: MedicineChestDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateCompositionPageViewModel::class.java)) {
            return UpdateCompositionPageViewModel(isUpdateList, listId, dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

