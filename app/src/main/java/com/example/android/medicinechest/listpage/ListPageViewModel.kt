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

//    private var tonight = MutableLiveData<SleepNight?>()
//    private val _products = MutableLiveData<List<Product>?>()
//    val products: LiveData<List<Product>?>
//        get() = _products

    private val _id = MutableLiveData<Long>(listId)
    private val id: LiveData<Long>
        get() = _id

    val products = if (id.value != 0L)
        dao.getProductsOfList(id.value!!)
    else
        dao.getAllProducts()

    private val _navigateToAdd = MutableLiveData<Boolean?>()
    val navigateToAdd: LiveData<Boolean?>
        get() = _navigateToAdd

//
//    val startButtonVisible = tonight.map { tonight ->
//        tonight == null
//    }
//    val stopButtonVisible = tonight.map { tonight ->
//        tonight != null
//    }
//    val clearButtonVisible = nights.map { nights ->
//        nights.isNotEmpty()
//    }

//    init {
//        initialize()
//    }
//
//    fun initialize() {
//        uiScope.launch {
//            _products.value = getAllProducts()
//        }
//    }
//
//    private suspend fun getAllProducts(): List<Product>? {
//        return withContext(Dispatchers.IO) {
//            dao.getAllProducts().value
//        }
//    }

//    private fun initializeTonight() {
//        uiScope.launch {
//            tonight.value = getTonightFromDatabase()
//        }
//    }
//
    fun doneNavigating() {
        _navigateToAdd.value = false
    }

    fun onTurnOnNavigateToAdd() {
        _navigateToAdd.value = true
    }

    fun setListId(listId: Long) {
        _id.value = listId
    }

    fun onClear() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                dao.clear()
            }
        }
    }

//
//    private suspend fun getTonightFromDatabase(): SleepNight? {
//        return withContext(Dispatchers.IO) {
//            var night = dao.getTonight()
//            if (night?.endTimeMillis != night?.startTimeMillis) {
//                night = null
//            }
//            night
//        }
//    }
//
//    fun onStartTracking() {
//        uiScope.launch {
//            val newNight = SleepNight()
//            insert(newNight)
//            tonight.value = getTonightFromDatabase()
//        }
//    }
//
//    private suspend fun insert(night: SleepNight) {
//        withContext(Dispatchers.IO) {
//            dao.insert(night)
//        }
//    }
//
//    fun onStopTracking() {
//        uiScope.launch {
//            val oldNight = tonight.value ?: return@launch
//            oldNight.endTimeMillis = System.currentTimeMillis()
//            update(oldNight)
//            _navigateToSleepQuality.value = oldNight
//        }
//    }
//
//    private suspend fun update(night: SleepNight) {
//        withContext(Dispatchers.IO) {
//            dao.update(night)
//        }
//    }
//
//    fun onClear() {
//        uiScope.launch {
//            clear()
//            tonight.value = null
//        }
//    }
//
//    private suspend fun clear() {
//        withContext(Dispatchers.IO) {
//            dao.clear()
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
