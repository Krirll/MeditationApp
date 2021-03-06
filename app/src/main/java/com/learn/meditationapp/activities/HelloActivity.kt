package com.learn.meditationapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.learn.meditationapp.R

class HelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hello_activity)
        findViewById<Button>(R.id.helloEnter).setOnClickListener {
            startActivity(
                Intent(
                    this, LoginActivity::class.java
                )
            )
            finish()
        }
        findViewById<Button>(R.id.helloRegister).setOnClickListener {
            startActivity(
                Intent(
                    this, RegisterActivity::class.java
                )
            )
        }
    }

    override fun onBackPressed() {
        finish()
    }
}