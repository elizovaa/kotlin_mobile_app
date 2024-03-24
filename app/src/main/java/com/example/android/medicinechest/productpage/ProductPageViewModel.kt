package com.example.android.medicinechest.productpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.medicinechest.database.MedicineChestDatabaseDao
import kotlinx.coroutines.*

class ProductPageViewModel(
    private val id: Long,
    private val dao: MedicineChestDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

//    private var _product: Product? = null
//    val product: Product
//        get() = _product!!
//
//    init {
//        uiScope.launch {
//            _product = dao.get(id)!!
//        }
//    }

    fun delete(id: Long) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                dao.deleteProductRefs(id)
                dao.delete(id)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
