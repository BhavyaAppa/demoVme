package com.base.hilt.ui.login

import com.base.hilt.base.BaseRepository
import com.base.hilt.model.response.BaseDataResponse
import com.base.hilt.network.ApiInterface
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import javax.inject.Inject


open class LoginRepository @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository() {

    open suspend fun callLoginAPI(): ResponseHandler<ResponseData<BaseDataResponse>?> {
        return makeAPICall {
            apiInterface.login()
        } //pass true if use retry network call functionality with attempt else not required any param.

    }
}