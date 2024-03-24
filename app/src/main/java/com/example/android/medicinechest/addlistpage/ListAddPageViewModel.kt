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

    private val _navigateToList = MutableLiveData<Boolean?>()
    val navigateToList: LiveData<Boolean?>
        get() = _navigateToList

    private var _list: Inventory = Inventory()
    val list: Inventory
        get() = _list

    fun doneNavigating() {
        _navigateToList.value = false
    }

    fun prepareForNavigationToList(list: Inventory, update: Boolean) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                if (update) {
                    dao.updateList(list)
                    _list = list
                }
                else {
                    val id = dao.insert(list)
                    _list = dao.getList(id)!!
                }
            }
            _navigateToList.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
