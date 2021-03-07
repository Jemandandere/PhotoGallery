package com.jemandandere.photogallery.data

import com.jemandandere.photogallery.model.Album
import com.jemandandere.photogallery.model.Photo
import io.reactivex.Single

interface DataRepository {
    fun getAlbumList(): Single<List<Album>>

    fun getPhotoList(album: Album): Single<List<Photo>>
}