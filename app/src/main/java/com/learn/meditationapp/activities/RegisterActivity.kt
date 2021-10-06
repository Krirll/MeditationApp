package com.learn.meditationapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learn.meditationapp.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
    }

    override fun onBackPressed() {
        finish()
    }
}