package com.learn.meditationapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.learn.meditationapp.R
import com.learn.meditationapp.adapters.PhotoAdapter
import com.learn.meditationapp.photo.DataBaseManager
import com.learn.meditationapp.photo.PhotoManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_activity)
        val index = intent.getIntExtra("INDEX", 0)
        findViewById<ImageView>(R.id.imageGallery).setImageURI(PhotoManager.list?.get(index)?.image)
        findViewById<Button>(R.id.cancel).setOnClickListener {
           finish()
        }
        findViewById<Button>(R.id.delete).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    DataBaseManager.db?.photoDao()
                        ?.delete(PhotoManager.list?.get(index)?.image?.path!!)
                }
                PhotoManager.list?.removeAt(index)
                PhotoManager.recyclerView?.adapter = PhotoAdapter(PhotoManager.list ?: mutableListOf())
                finish()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}