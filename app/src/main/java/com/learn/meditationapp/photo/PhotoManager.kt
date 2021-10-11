package com.learn.meditationapp.photo

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView

class TruePhoto(var time : String, var image : Bitmap)

object PhotoManager {
    var recyclerView : RecyclerView? = null
    var list : MutableList<TruePhoto>? = null

    fun getPhotosByUri(listFromDB : List<Photo>?) : MutableList<TruePhoto> {
        if (listFromDB != null) {
            for (savedPhoto in listFromDB) {
                //todo находить изображение по uri в памяти и добавлять в MutableList<TruePhoto>
                //val i = Uri.parse(uri) -> string to uri
            }
        }
        else {
            list = list ?: mutableListOf()
        }
        return list as MutableList<TruePhoto>
    }

    fun savePhotoInMemory(uriOfPhoto : Uri) {
        //todo сюда попадают Uri, по Uri я нахожу картинку, создаю файл и сохраняю в памяти
    }

}