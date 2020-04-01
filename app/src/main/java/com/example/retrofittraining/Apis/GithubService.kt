package com.example.retrofittraining.Apis

import com.example.retrofittraining.Models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): Call<User>

}