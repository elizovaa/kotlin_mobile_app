package com.example.android.medicinechest.searchpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.medicinechest.database.Product
import com.example.android.medicinechest.database.ProductDatabaseDao
import kotlinx.coroutines.*
import androidx.lifecycle.MutableLiveData

class SearchPageViewModel(
    private val dao: ProductDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
