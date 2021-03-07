package com.jemandandere.photogallery.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Keep
@Entity(tableName = "album")
data class Album(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String
) : Serializable