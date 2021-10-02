package com.learn.meditationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        findViewById<Button>(R.id.signIn).setOnClickListener {
            val email = findViewById<EditText>(R.id.login)
            val password = findViewById<EditText>(R.id.password)
            if (email.text.toString().matches("""\w+@\w+\.\w+""".toRegex()) && password.text.toString().matches("""\w+""".toRegex())) {
                RetrofitObject.init()
                val account = RetrofitObject.callUser(email.text.toString(), password.text.toString())
                if (account != null) {
                    startActivity(
                        Intent(this, MainActivity::class.java).apply {
                            putExtra(MainActivity.ACCOUNT, account)
                        }
                    )
                }
                else {
                    email.setText("")
                    password.setText("")
                    Toast.makeText(
                        this,
                        "Проверьте интернет соединение или логин/пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else {
                email.setText("")
                password.setText("")
                Toast.makeText(
                    this,
                    "Неправильно заполнены поля ввода",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        findViewById<Button>(R.id.register).setOnClickListener {
            startActivity(
                Intent(
                    this, RegisterActivity::class.java
                )
            )
        }
    }
}