package com.learn.meditationapp

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val user = intent.getSerializableExtra(ACCOUNT) as User
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                findViewById<ImageView>(R.id.avatar).setImageDrawable(
                    Drawable.createFromStream(URL(user.avatar).content as InputStream, "src")
                )
            }
        }
        //findViewById<TextView>(R.id.welcomeTextView).text = user.nickName
        //TODO здесь будет переход по фрагментам
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main -> {
                    Toast.makeText(this, "main", Toast.LENGTH_SHORT).show(); true}
                R.id.music -> {Toast.makeText(this, "music", Toast.LENGTH_SHORT).show(); true}
                R.id.profile -> {Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show(); true}
                else -> true
            }
        }
    }

    companion object {
        const val ACCOUNT = "ACCOUNT"
    }
}