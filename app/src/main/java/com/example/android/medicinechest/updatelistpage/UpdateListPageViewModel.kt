package com.example.android.medicinechest.updatelistpage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.medicinechest.database.InventoryProductCrossRef
import com.example.android.medicinechest.database.MedicineChestDatabaseDao
import kotlinx.coroutines.*

class UpdateListPageViewModel(
    private val listId: Long,
    private val dao: MedicineChestDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val products = dao.getProductChecksOfList(listId)

    private val _navigateToSave = MutableLiveData<Boolean?>()
    val navigateToSave: LiveData<Boolean?>
        get() = _navigateToSave

    fun doneNavigating() {
        _navigateToSave.value = false
    }

    fun prepareForNavigationToList(checks: Map<Long, Boolean>) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                for (productCheck in products.value!!) {
                    val oldCheck = productCheck.isChecked
                    val newCheck = checks[productCheck.id]
                    if (newCheck == null)
                        continue
                    val ref = InventoryProductCrossRef(listId, productCheck.id)
                    if (!oldCheck && newCheck)
                        dao.insert(ref)
                    if (oldCheck && !newCheck)
                        dao.delete(ref)
                }
            }
            _navigateToSave.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
