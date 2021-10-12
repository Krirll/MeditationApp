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
import com.learn.meditationapp.photo.PhotoManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var user : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        user = intent.getSerializableExtra(ACCOUNT) as User
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
        findViewById<Button>(R.id.exit).setOnClickListener {
            //todo сохранение почты пользователя
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CODE && data?.data != null) {
            PhotoManager.savePhotoInMemory(data.data!!, this, user.nickName!!)
        }
    }

    companion object {
        const val ACCOUNT = "ACCOUNT"
        const val CODE = 100
    }

    override fun onBackPressed() {
        PhotoManager.list = null
        finish()
    }
}