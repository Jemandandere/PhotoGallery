package com.jemandandere.photogallery.data.local

import android.annotation.SuppressLint
import android.content.Context
import com.jemandandere.photogallery.data.DataRepository
import com.jemandandere.photogallery.data.PrivateRepository
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class DataLocalRepository(context: Context) : DataRepository, PrivateRepository {
    val dao : RoomDao = LocalDatabase.getInstance(context).getRoomDao()

    override fun getAlbumList(): Observable<List<Album>> {
        return dao.getAlbumList()
    }

    override fun getPhotoList(album: Album): Observable<List<Photo>> {
        return dao.getPhotoList(album.id)
    }

    override fun check(album: Album): Single<Boolean> {
        return dao.check(album.id).map{ x -> x > 0}
    }

    override fun insert(album: Album, photoList: List<Photo>): Completable {
        return dao.insert(album).andThen(dao.insert(photoList))
    }

    override fun delete(album: Album, photoList: List<Photo>): Completable {
        return dao.delete(album).andThen(dao.delete(photoList))
    }
}