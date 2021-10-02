package com.learn.meditationapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.Serializable

class GetUser : Serializable {
    @SerializedName ("id")
    @Expose
    var id : Int? = null

    @SerializedName ("email")
    @Expose
    var email : String? = null

    @SerializedName ("nickName")
    @Expose
    var nickName : String? = null

    @SerializedName ("avatar")
    @Expose
    var avatar : String? = null

    @SerializedName ("token")
    @Expose
    var token : Int? = null
}

interface Queries {
    @POST("/user/login")
    fun postUser(@Body email : String, password : String) : Call<GetUser>
}