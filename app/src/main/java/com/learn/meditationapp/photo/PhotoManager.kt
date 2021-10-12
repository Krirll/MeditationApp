package com.learn.meditationapp.photo

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore.Images.Media.getBitmap
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.learn.meditationapp.adapters.PhotoAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class TruePhoto(var time : String, var image : Uri)

object PhotoManager {
    var recyclerView : RecyclerView? = null
    var list : MutableList<TruePhoto>? = null

    fun getPhotosByUri(listFromDB : List<Photo>?) {
        if (list == null)
            list = mutableListOf()
        if (listFromDB != null && list?.isEmpty() == true) {
            for (savedPhoto in listFromDB) {
                list?.add(
                    TruePhoto(
                        savedPhoto.time,
                        Uri.parse(savedPhoto.photoUri)
                    )
                )
            }
        }
        recyclerView?.adapter = PhotoAdapter(list ?: mutableListOf())
    }

    fun savePhotoInMemory(uriOfPhoto : Uri, context: Context, nick : String) {
        val bitmap = getBitmap(context.contentResolver, uriOfPhoto)
        if (bitmap != null) {
            val wrapper = ContextWrapper(context)
            var file = wrapper.getDir("app_images_$nick", Context.MODE_PRIVATE)
            file = File(file, "${UUID.randomUUID()}.jpg")
            try {
                val stream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()

                val time = SimpleDateFormat("hh:mm", Locale.ROOT).format(Date())
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO) {
                        DataBaseManager.db?.photoDao()?.insert(
                            Photo(
                                time,
                                file.absolutePath
                            )
                        )
                    }
                    list?.add(
                        TruePhoto(
                            time,
                            file.toUri()
                        )
                    )
                    recyclerView?.adapter = PhotoAdapter(list!!)
                }
            }
            catch (e: IOException) {
                 e.printStackTrace()
            }
        }
    }
}