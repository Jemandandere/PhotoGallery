package com.jemandandere.photogallery.data

import androidx.lifecycle.LiveData
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface AlbumRepository {
    fun getAlbumList(): Observable<List<Album>>

    fun getPhotoList(albumId: Int): Observable<List<Photo>>
}