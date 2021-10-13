package com.learn.meditationapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.learn.meditationapp.API.RetrofitObject
import com.learn.meditationapp.API.User
import com.learn.meditationapp.R
import com.learn.meditationapp.loginData.LoginObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        val accountLoaded = LoginObject.loadData(this)
        lateinit var intent : Intent
        if (accountLoaded.email == "") {
            intent = Intent(this, HelloActivity::class.java)
            start(intent)
        }
        else {
            if (accountLoaded.password == "") {
                intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra(
                        LoginActivity.EMAIL,
                        accountLoaded.email
                    )
                }
                start(intent)
            }
            else {
                RetrofitObject.init()
                val call = RetrofitObject.service?.postUser(
                    User(
                        accountLoaded.email!!,
                        accountLoaded.password!!
                    )
                )
                var account: User?
                call?.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            account = response.body() as User
                            if (account != null) {
                                account?.password = accountLoaded.password
                                intent = Intent(
                                    this@SplashActivity,
                                    MainActivity::class.java
                                ).apply { putExtra(MainActivity.ACCOUNT, account) }
                                start(intent)
                            }
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(
                            this@SplashActivity,
                            "Проверьте интернет соединение",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }

    private fun start(intent : Intent) {
        Handler().postDelayed({
            startActivity(intent)
            finish()
        },1500)
    }

    override fun onBackPressed() {}

}