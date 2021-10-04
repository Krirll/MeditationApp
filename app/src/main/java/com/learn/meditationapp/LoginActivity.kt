package com.learn.meditationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        findViewById<Button>(R.id.signIn).setOnClickListener {
            val email = findViewById<EditText>(R.id.login)
            val password = findViewById<EditText>(R.id.password)
            if (email.text.toString().matches("""\w+@\w+\.\w+""".toRegex()) && password.text.toString().matches("""\w+""".toRegex())) {
                RetrofitObject.init()
                var account: User? = null
                val user = User(email.text.toString(), password.text.toString())
                val call = RetrofitObject.service?.postUser(user)
                call?.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            account = response.body() as User
                            if (account != null) {
                                startActivity(
                                    Intent(this@LoginActivity, MainActivity::class.java).apply {
                                        putExtra(MainActivity.ACCOUNT, account)
                                    }
                                )
                            } else {
                                email.setText("")
                                password.setText("")
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Проверьте интернет соединение или логин/пароль",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        else
                            Toast.makeText(this@LoginActivity, "Ошибка загрузки", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                })
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