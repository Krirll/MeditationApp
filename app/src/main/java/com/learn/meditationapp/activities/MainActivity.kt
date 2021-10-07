package com.learn.meditationapp.activities

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
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
            when (it.itemId) {
                R.id.main -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainFragment>(R.id.fragment,  args = bundleOf("NAME" to user.nickName))
                    }
                    true
                }
                R.id.music -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MusicFragment>(R.id.fragment)
                    }
                    true
                }
                R.id.profile -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<ProfileFragment>(R.id.fragment,  args = bundleOf("NAME" to user.nickName, "AVATAR" to user.avatar))
                    }
                    true
                }
                else -> true
            }
        }
    }

    companion object {
        const val ACCOUNT = "ACCOUNT"
    }
}