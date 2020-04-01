package com.example.retrofittraining.Apis

import com.example.retrofittraining.Models.User
import okhttp3.ResponseBody
import retrofit2.Retrofit

interface OnNetworkCallbackListener<T> {

    fun onResponse(data: T, retrofit: Retrofit)

    fun onBodyError(responseBodyError: ResponseBody)

    fun onBodyErrorNull()

    fun onFailure(t: Throwable)

}