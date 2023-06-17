package org.d3if3058.myapplication.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.d3if3058.myapplication.model.Resep
import org.d3if3058.myapplication.network.ApiStatus
import org.d3if3058.myapplication.network.ResepApi
import org.d3if3058.myapplication.network.UpdateWorker
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private val data = MutableLiveData<List<Resep>>()
    private val status = MutableLiveData<ApiStatus>()
    init {
        retrieveData()

    }
    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)

            try {
                data.postValue(ResepApi.service.getResep())
                status.postValue(ApiStatus.SUCCESS)

            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)


            }
        }
    }



    fun getData(): LiveData<List<Resep>> = data

    fun getStatus(): LiveData<ApiStatus> = status



    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}
