package com.jemandandere.photogallery.data.remote

import com.jemandandere.photogallery.App
import com.jemandandere.photogallery.data.DataRepository
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import io.reactivex.Observable
import io.reactivex.Single

class DataRemoteRepository : DataRepository {
    val retrofitDao = App.retrofit.create(RetrofitDao::class.java)

    override fun getAlbumList(): Single<List<Album>> = retrofitDao.getAlbumList()

    override fun getPhotoList(album: Album): Single<List<Photo>> =
        retrofitDao.getPhotoList(album.id)
}