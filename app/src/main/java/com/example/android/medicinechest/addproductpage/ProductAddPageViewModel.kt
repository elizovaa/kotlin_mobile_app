package com.example.android.medicinechest.addproductpage

import android.app.Application
import android.util.Log
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

    private val _navigateToProduct = MutableLiveData<Boolean?>()
    val navigateToProduct: LiveData<Boolean?>
        get() = _navigateToProduct

    private var _product: Product = Product()
    val product: Product
        get() = _product

    fun doneNavigating() {
        _navigateToProduct.value = false
    }

    fun prepareForNavigationToProduct(product: Product, update: Boolean) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                if (update) {
                    dao.update(product)
                    _product = product
                }
                else {
                    val id = dao.insert(product)
                    _product = dao.get(id)!!
                    Log.i("ProductAddPageViewModel", _product.productId.toString())

                }
            }
            _navigateToProduct.value = true
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
