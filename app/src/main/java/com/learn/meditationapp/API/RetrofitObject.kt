package com.learn.meditationapp.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    var service : Queries? = null
    private var retrofit : Retrofit? = null

    fun init() {
        retrofit = Retrofit.Builder()
            .baseUrl("http://mskko2021.mad.hakta.pro/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit?.create(Queries::class.java)
    }
}