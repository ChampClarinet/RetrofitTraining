package com.example.retrofittraining.Apis

import com.example.retrofittraining.Models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConnectionManager {

    fun callServer(listener: OnNetworkCallbackListener<User>, username: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val git: GithubService = retrofit.create(GithubService::class.java)
        val call = git.getUser(username)
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                listener.onFailure(t)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.body() == null) {
                    val responseBody: ResponseBody? = response.errorBody()
                    if (responseBody != null) {
                        listener.onBodyError(responseBody)
                    } else listener.onBodyErrorNull()
                } else {
                    val user = response.body() as User
                    listener.onResponse(user, retrofit)
                }
            }
        })
    }

}