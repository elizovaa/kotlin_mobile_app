package com.example.android.medicinechest.searchpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.medicinechest.database.MedicineChestDatabaseDao
import kotlinx.coroutines.*

class SearchPageViewModel(
    private val dao: MedicineChestDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var products = dao.getAllProducts()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
