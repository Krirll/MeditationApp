package com.learn.meditationapp.photo

import android.content.Context
import androidx.room.Room

object DataBaseManager {
    var db : DataBase? = null

    fun init(context: Context) {
        if (db == null)
            db = Room.databaseBuilder(context, DataBase::class.java, "photos").build()
    }
}