package com.jemandandere.photogallery.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Keep
@Entity(tableName = "album")
data class Album(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo val userId: Int,
    @ColumnInfo val id: Int,
    @ColumnInfo val title: String
) : Serializable