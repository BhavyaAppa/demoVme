package com.base.hilt.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.Resource
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor() : ViewModelBase() {

    private val status = MutableLiveData<Resource<String>>()
    fun getStatus(): LiveData<Resource<String>> {
        return status
    }
//    Use the SingleLiveEvent when you want to get data once
    val changeLanguageClick = SingleLiveEvent<Boolean>()

    fun changeLangClick() {
        changeLanguageClick.call()
    }

    fun startLongRunningTask() {
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            // do a long running task
            doLongRunningTask()
                .flowOn(Dispatchers.Default)
                .catch {
                    status.postValue(Resource.error("Something Went Wrong", null))
                }
                .collect {
                    status.postValue(Resource.success("Task Completed"))
                }
        }
    }



    private fun doLongRunningTask(): Flow<Int> {
        return flow {
            // your code for doing a long running task
            // Added delay to simulate
            delay(5000)
            emit(0)
        }
    }
}