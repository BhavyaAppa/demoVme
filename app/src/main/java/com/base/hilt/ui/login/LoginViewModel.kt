package com.base.hilt.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.base.hilt.R
import com.base.hilt.base.ViewModelBase
import com.base.hilt.model.response.BaseDataResponse
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.utils.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the sign in.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) :
    ViewModelBase() {

    private val _loginResponse =
        MutableLiveData<ResponseHandler<ResponseData<BaseDataResponse>?>>()
    val loginResponse: LiveData<ResponseHandler<ResponseData<BaseDataResponse>?>>
        get() = _loginResponse
     val email = MutableLiveData<String>()
     val password = MutableLiveData<String>()

    fun onLoginClick(email:String,password:String) {
//        hideKeyboard()
        when {
            !Validation.isNotNull(email.trim()) -> {
                showSnackBarMessage(R.string.alert_enter_email)
            }
            !Validation.isEmailValid(email.trim()) -> {
                showSnackBarMessage(R.string.alert_enter_valid_email)

            }
            !Validation.isNotNull(password.trim()) -> {
                showSnackBarMessage(R.string.alert_enter_password)

            }
            !Validation.isValidPassword(password.trim()) -> {
                showSnackBarMessage(R.string.alert_enter_valid_password)
            }
            else ->
            {
                callLoginAPI()
//                showSnackBarMessage(R.string.label_login_successfully)
            }
        }
    }

    fun callLoginAPI() {
        viewModelScope.launch {
            _loginResponse.postValue(ResponseHandler.Loading)
            _loginResponse.value = repository.callLoginAPI()
        }
    }
}