package com.learn.meditationapp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private var service : Queries? = null
    private var retrofit : Retrofit? = null

    fun init() {
        retrofit = Retrofit.Builder()
            .baseUrl("http://mskko2021.mad.hakta.pro/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit?.create(Queries::class.java)
    }

    fun callUser(email : String, password : String) : GetUser? {
        val call = service?.postUser(email, password)
        var resultFlag = false
        var account : GetUser? = null
        call?.enqueue(object : Callback<GetUser> {
            override fun onResponse(call: Call<GetUser>, response: Response<GetUser>) {
                if (response.isSuccessful) {
                    account = response.body() as GetUser
                }
            }

            override fun onFailure(call: Call<GetUser>, t: Throwable) {}
        })
        return account
    }
}