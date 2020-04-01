package com.example.retrofittraining.Models

import com.google.gson.annotations.Expose

class User {
    @Expose
    var name: String? = null
    @Expose
    var blog: String? = null
    @Expose
    var company: String? = null
}