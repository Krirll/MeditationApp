package com.learn.meditationapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.learn.meditationapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //todo если почта и пароль есть в памяти то переходить на MainActivity
        //      если есть только почта то на экран Login
        //      если нет ни почты ни пароля то на экран Hello
        setContentView(R.layout.splash_activity)
        Handler().postDelayed({
            startActivity(Intent(this, HelloActivity::class.java))
            finish()
        },1500)
    }

    override fun onBackPressed() {}


}