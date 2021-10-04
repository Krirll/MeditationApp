package com.learn.meditationapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.io.Serializable

class User(email: String, password: String) : Serializable {
    @SerializedName ("id")
    @Expose
    var id : String? = null

    @SerializedName ("email")
    @Expose
    var email : String? = email

    @SerializedName ("password")
    @Expose
    var password : String? = password

    @SerializedName ("nickName")
    @Expose
    var nickName : String? = null

    @SerializedName ("avatar")
    @Expose
    var avatar : String? = null

    @SerializedName ("token")
    @Expose
    var token : String? = null
}

interface Queries {
    @POST("user/login")
    fun postUser(@Body user : User ) : Call<User>
}