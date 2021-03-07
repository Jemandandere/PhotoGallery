package com.jemandandere.photogallery.data.local

import android.content.Context
import com.jemandandere.photogallery.data.DataRepository
import com.jemandandere.photogallery.data.PrivateRepository
import com.jemandandere.photogallery.model.Album
import com.jemandandere.photogallery.model.Photo
import io.reactivex.Completable
import io.reactivex.Single

class DataLocalRepository(context: Context) : DataRepository, PrivateRepository {
    private val dao : RoomDao = LocalDatabase.getInstance(context).getRoomDao()

    override fun getAlbumList(): Single<List<Album>> {
        return dao.getAlbumList()
    }

    override fun getPhotoList(album: Album): Single<List<Photo>> {
        return dao.getPhotoList(album.id)
    }

    override fun isSave(album: Album): Single<Boolean> {
        return dao.isSave(album.id).map{i -> i > 0}
    }

    override fun insert(album: Album, photoList: List<Photo>): Completable {
        return dao.insert(album).andThen(dao.insert(photoList))
    }

    override fun delete(album: Album, photoList: List<Photo>): Completable {
        return dao.delete(album).andThen(dao.delete(photoList))
    }
}