package com.example.android.medicinechest.updatecompositionpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.medicinechest.database.InventoryProductCrossRef
import com.example.android.medicinechest.database.MedicineChestDatabaseDao
import kotlinx.coroutines.*

class UpdateCompositionPageViewModel(
    private val isUpdateList: Boolean,
    private val objectId: Long,
    private val dao: MedicineChestDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val objects =
        if (isUpdateList)
            dao.getProductChecksOfList(objectId)
        else
            dao.getListChecksOfProduct(objectId)

    private val _navigateToSave = MutableLiveData<Boolean?>()
    val navigateToSave: LiveData<Boolean?>
        get() = _navigateToSave

    fun doneNavigating() {
        _navigateToSave.value = false
    }

    fun prepareForNavigationToObject(checks: List<Long>) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                for (objectCheck in objects.value!!) {
                    val oldCheck = objectCheck.isChecked
                    val newCheck = checks.contains(objectCheck.id)
                    if (!newCheck)
                        continue
                    val ref =
                        if (isUpdateList)
                            InventoryProductCrossRef(objectId, objectCheck.id)
                        else
                            InventoryProductCrossRef(objectCheck.id, objectId)
                    if (!oldCheck)
                        dao.insert(ref)
                    if (oldCheck)
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
