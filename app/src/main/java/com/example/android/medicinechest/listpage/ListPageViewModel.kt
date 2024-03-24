package com.example.android.medicinechest.listpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.medicinechest.database.MedicineChestDatabaseDao
import kotlinx.coroutines.*

class ListPageViewModel(
    private val listId: Long,
    private val dao: MedicineChestDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _id = MutableLiveData<Long>(listId)
    private val id: LiveData<Long>
        get() = _id

    val products = if (id.value != 0L)
        dao.getProductsOfList(id.value!!)
    else
        dao.getAllProducts()

    fun onClear() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                dao.clear()
            }
        }
    }

    fun delete(id: Long) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                dao.deleteListRefs(id)
                dao.deleteList(id)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
