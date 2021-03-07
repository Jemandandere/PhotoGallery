package com.jemandandere.photogallery.data.local

import androidx.room.*
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface RoomDao {
    @Query("select * from album")
    fun getAlbumList(): Observable<List<Album>>

    @Query("select * from photo where albumId = :albumId")
    fun getPhotoList(albumId: Int): Observable<List<Photo>>

    @Query("select count(*) from album where id = :albumId")
    fun check(albumId: Int): Single<Int>

    @Insert
    fun insert(album: Album): Completable

    @Insert
    fun insert(photoList: List<Photo>) : Completable

    @Delete
    fun delete(album: Album) : Completable

    @Delete
    fun delete(photoList: List<Photo>) : Completable
}