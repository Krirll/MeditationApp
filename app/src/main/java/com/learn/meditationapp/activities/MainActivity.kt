package com.learn.meditationapp.activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.learn.meditationapp.R
import com.learn.meditationapp.API.User
import com.learn.meditationapp.fragments.MainFragment
import com.learn.meditationapp.fragments.MusicFragment
import com.learn.meditationapp.fragments.ProfileFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val user = intent.getSerializableExtra(ACCOUNT) as User
        CoroutineScope(Dispatchers.Main).launch {
            val image : Drawable
            withContext(Dispatchers.IO) {
                image =
                    Drawable.createFromStream(URL(user.avatar).content as InputStream, "src")
            }
            findViewById<ImageView>(R.id.avatar).setImageDrawable(image)
        }
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MainFragment>(R.id.fragment, args = bundleOf("NAME" to user.nickName))
        }
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
            val button = findViewById<Button>(R.id.exit)
            when (it.itemId) {
                R.id.main -> {
                    if (button.visibility == View.VISIBLE)
                        button.visibility = View.GONE
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainFragment>(R.id.fragment,  args = bundleOf("NAME" to user.nickName))
                    }
                    true
                }
                R.id.music -> {
                    if (button.visibility == View.VISIBLE)
                        button.visibility = View.GONE
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MusicFragment>(R.id.fragment)
                    }
                    true
                }
                R.id.profile -> {
                    if (button.visibility == View.GONE)
                        button.visibility = View.VISIBLE
                    val activity = this
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<ProfileFragment>(R.id.fragment,
                            args = bundleOf(
                                "NAME" to user.nickName, "AVATAR" to user.avatar)
                        )
                    }
                    true
                }
                else -> true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CODE && data?.data != null) {
            //SimpleDateFormat("hh:mm", Locale.ROOT).format(Date()),
            //Drawable.createFromStream(contentResolver.openInputStream(data.data!!), "")
            //todo 1. передавать в функцию PhotoManager полученную картинку (там же преобразовать ее в Bitmap)
            //     2. сохранять ее в БД
            //     3. сохранять ее в память, добавить в список и обновлять адаптер
        }
    }

    companion object {
        const val ACCOUNT = "ACCOUNT"
        const val CODE = 100
    }
}