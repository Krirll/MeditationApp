package com.learn.meditationapp.API

import android.app.Activity
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    var service : Queries? = null
    private var retrofit : Retrofit? = null

    fun init() {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            service = retrofit?.create(Queries::class.java)
        }
    }
}