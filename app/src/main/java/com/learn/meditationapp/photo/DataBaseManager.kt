package com.learn.meditationapp.photo

import android.content.Context
import androidx.room.Room

object DataBaseManager {
    var db : DataBase? = null

    fun init(context: Context, nick : String) {
            db = Room.databaseBuilder(context, DataBase::class.java, "photos_$nick").build()
    }
}