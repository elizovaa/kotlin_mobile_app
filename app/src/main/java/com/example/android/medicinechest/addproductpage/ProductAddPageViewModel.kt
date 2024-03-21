package com.example.android.medicinechest.addproductpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.medicinechest.database.Product
import com.example.android.medicinechest.database.MedicineChestDatabaseDao
import kotlinx.coroutines.*
import androidx.lifecycle.MutableLiveData

class ProductAddPageViewModel(
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

    fun prepareForNavigationToAdd(name: String, type: String, amount: Int, dosage: String, comment: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val newProduct = Product()
                val insertProject = newProduct.copy(
                    name = name,
                    type = type,
                    amount = amount,
                    dosage = dosage,
                    comment = comment
                )
                dao.insert(insertProject)
            }
            _navigateToAdd.value = true
        }
    }

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
