package com.learn.meditationapp.activities

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import com.learn.meditationapp.R
import com.learn.meditationapp.photo.DataBaseManager
import com.learn.meditationapp.photo.PhotoManager
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ShowActivity : AppCompatActivity() {
    private var isScaled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_activity)
        val index = intent.getIntExtra("INDEX", 0)
        val image = findViewById<ImageView>(R.id.imageGallery)
        Picasso.with(this)
            .load(PhotoManager.list?.get(index)?.image)
            .fit()
            .centerCrop()
            .into(image)
        var doubleClick = false
        image.setOnClickListener {
            val r = Runnable { doubleClick = false }
            if (doubleClick) {
                if (!isScaled) {
                    image.scaleX *= 2
                    image.scaleY *= 2
                    isScaled = true
                }
                else {
                    image.scaleX /= 2
                    image.scaleY /= 2
                    isScaled = false
                }
                doubleClick = false
            } else {
                doubleClick = true
                Handler().postDelayed(r, 500)
            }
        }
        findViewById<Button>(R.id.cancel).setOnClickListener {
           finish()
        }
        findViewById<Button>(R.id.delete).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    DataBaseManager.db?.photoDao()?.delete(PhotoManager.list?.get(index)?.image?.toString()!!)
                }
                File(PhotoManager.list?.get(index)?.image?.path!!).delete()
                PhotoManager.list?.removeAt(index)
                PhotoManager.recyclerView?.adapter?.notifyItemRemoved(index)
                finish()
            }
        }

    }

    override fun onBackPressed() {
        finish()
    }
}