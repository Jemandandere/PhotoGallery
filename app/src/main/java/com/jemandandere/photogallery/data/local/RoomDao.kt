package com.jemandandere.photogallery.data.local

import androidx.room.*
import com.jemandandere.photogallery.model.Album
import com.jemandandere.photogallery.model.Photo
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RoomDao {
    @Query("select * from album")
    fun getAlbumList(): Single<List<Album>>

    @Query("select count(*) from album where id = :albumId")
    fun isSave(albumId: Int): Single<Int>

    @Query("select * from photo where albumId = :albumId")
    fun getPhotoList(albumId: Int): Single<List<Photo>>

    @Insert
    fun insert(album: Album): Completable

    @Insert
    fun insert(photoList: List<Photo>) : Completable

    @Delete
    fun delete(album: Album) : Completable

    @Delete
    fun delete(photoList: List<Photo>) : Completable
}