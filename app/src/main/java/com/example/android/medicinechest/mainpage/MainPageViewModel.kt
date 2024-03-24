package com.example.android.medicinechest.mainpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.medicinechest.database.MedicineChestDatabaseDao
import kotlinx.coroutines.*

class MainPageViewModel(
    private val dao: MedicineChestDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    val lists = dao.getAllLists()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
