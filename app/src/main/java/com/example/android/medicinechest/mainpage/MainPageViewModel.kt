package com.example.android.medicinechest.mainpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.medicinechest.database.Product
import com.example.android.medicinechest.database.ProductDatabaseDao
import kotlinx.coroutines.*

class MainPageViewModel(
    private val dao: ProductDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

//    private var tonight = MutableLiveData<SleepNight?>()
//    val nights = dao.getAllNights()
//
//    private val _navigateToSleepQuality = MutableLiveData<SleepNight?>()
//    val navigateToSleepQuality: LiveData<SleepNight?>
//        get() = _navigateToSleepQuality
//
//    val nightsString = nights.map { nights ->
//        formatNights(nights, application.resources)
//    }
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

    init {
//        initializeTonight()
    }

//    private fun initializeTonight() {
//        uiScope.launch {
//            tonight.value = getTonightFromDatabase()
//        }
//    }
//
//    fun doneNavigating() {
//        _navigateToSleepQuality.value = null
//    }
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
