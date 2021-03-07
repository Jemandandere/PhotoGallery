package com.jemandandere.photogallery.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jemandandere.photogallery.model.Album
import com.jemandandere.photogallery.model.Photo

@Database(entities = [Album::class, Photo::class], version = 1)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun getRoomDao(): RoomDao
    companion object {
        @Volatile
        private var database: LocalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): LocalDatabase{
            return if (database==null){
                database = Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java,
                    "photoGalleryDatabase"
                ).build()
                database as LocalDatabase
            } else database as LocalDatabase
        }
    }
}