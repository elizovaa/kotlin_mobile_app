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

                }
            }
            _navigateToProduct.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
