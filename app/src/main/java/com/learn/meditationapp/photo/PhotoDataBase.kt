package com.learn.meditationapp.photo

import androidx.room.*
import java.io.Serializable

@Entity
data class Photo(
    @ColumnInfo (name = "time") var time : String,
    @ColumnInfo (name = "photoUri") var photoUri : String
) : Serializable
{
    @PrimaryKey (autoGenerate = true) var id : Int? = null
}

@Dao
interface PhotoDao {

    @Insert
    fun insert(photo: Photo)

    @Query ("DELETE FROM Photo WHERE photoUri = :uri")
    fun delete(uri : String)

    @Query ("SELECT * FROM Photo")
    fun getAll() : List<Photo>

}

@Database (entities = [Photo::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun photoDao() : PhotoDao
}
