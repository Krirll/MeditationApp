package com.learn.meditationapp

import android.graphics.drawable.Drawable
import androidx.room.*

@Entity
data class Photo(
    @ColumnInfo (name = "time") var time : String,
    @ColumnInfo (name = "image") var image : String
)
{
    @PrimaryKey (autoGenerate = true) var id : Int = 0
}

@Dao
interface PhotoDao {

    @Insert
    fun add(photo : Photo)

    @Delete
    fun delete(photo : Photo)

    @Query ("SELECT * FROM Photo")
    fun getAll() : List<Photo>

}

@Database (entities = [Photo::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun photoDao() : PhotoDao
}