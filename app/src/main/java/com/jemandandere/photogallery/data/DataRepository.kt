package com.jemandandere.photogallery.data

import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import io.reactivex.Observable

interface DataRepository {
    fun getAlbumList(): Observable<List<Album>>

    fun getPhotoList(album: Album): Observable<List<Photo>>
}