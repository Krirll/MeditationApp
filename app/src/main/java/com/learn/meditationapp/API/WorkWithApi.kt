package com.learn.meditationapp.API

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.Serializable

class User(email: String, password: String) : Serializable {

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
}

class Feelings : Serializable {
    @SerializedName ("data")
    @Expose
    var data : List<Feel>? = null
}

class Feel : Serializable {

    @SerializedName ("title")
    @Expose
    var title : String? = null

    @SerializedName ("image")
    @Expose
    var image : String? = null

    @SerializedName ("position")
    @Expose
    var position : Double? = null
}

interface Queries {
    @POST("user/login")
    fun postUser(@Body user : User) : Call<User>

    @GET("feelings")
    fun getFeelings() : Call<Feelings>


}