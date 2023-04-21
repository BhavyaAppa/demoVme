package com.base.hilt.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.base.hilt.base.ViewModelBase
import com.base.hilt.model.response.MovieListResponse
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModelBase() {
/*
    The objective of either of these is to keep the mutability private,
    so consumers of this class do not accidentally update the MutableLiveData themselves.
*/


    private val _responseLiveHomeVendorListResponse =
    MutableLiveData<ResponseHandler<ResponseData<MovieListResponse>?>>()
    val responseLiveHomeVendorListResponse: LiveData<ResponseHandler<ResponseData<MovieListResponse>?>>
        get() = _responseLiveHomeVendorListResponse

    fun callHomeScreenAPI() {
        viewModelScope.launch {
            _responseLiveHomeVendorListResponse.postValue(ResponseHandler.Loading)
                _responseLiveHomeVendorListResponse.postValue(homeRepository.callHomeScreenAPI())
        }
    }
}