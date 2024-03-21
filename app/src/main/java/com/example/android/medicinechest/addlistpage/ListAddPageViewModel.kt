package com.example.android.medicinechest.addlistpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.medicinechest.database.MedicineChestDatabaseDao
import kotlinx.coroutines.*
import androidx.lifecycle.MutableLiveData
import com.example.android.medicinechest.database.Inventory

class ListAddPageViewModel(
    private val dao: MedicineChestDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _navigateToAdd = MutableLiveData<Boolean?>()
    val navigateToAdd: LiveData<Boolean?>
        get() = _navigateToAdd

    fun doneNavigating() {
        _navigateToAdd.value = false
    }

    fun prepareForNavigationToAdd(name: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val newList = Inventory()
                val insertList = newList.copy(
                    name = name
                )
                dao.insert(insertList)
            }
            _navigateToAdd.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
