package com.learn.meditationapp.loginData

import android.content.Context

object LoginObject {

    data class Data (val email : String?, val password: String?)

    private const val EMAIL = "EMAIL"
    private const val PW = "PW"
    private const val ACCOUNT = "ACCOUNT"

    fun saveData(email : String, password : String = "", context: Context) {
        context.getSharedPreferences(
            ACCOUNT,
            Context.MODE_PRIVATE
        ).edit()
            .apply {
            putString(EMAIL, email)
            putString(PW, password)
        }.apply()
    }

    fun loadData(context: Context) : Data {
        var email : String?
        var password : String?
        context.getSharedPreferences(
            ACCOUNT,
            Context.MODE_PRIVATE
        ).apply {
            email = getString(EMAIL, "")
            password = getString(PW, "")
        }
        return Data(email, password)
    }
}