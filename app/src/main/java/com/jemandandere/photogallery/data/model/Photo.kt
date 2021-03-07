package com.jemandandere.photogallery.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "photo")
data class Photo(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo val albumId: Int,
    @ColumnInfo val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val url: String,
    @ColumnInfo val thumbnailUrl: String
)