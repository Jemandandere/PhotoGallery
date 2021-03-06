package com.jemandandere.photogallery.data.remote

import com.jemandandere.photogallery.App
import com.jemandandere.photogallery.data.AlbumRepository
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class AlbumRemoteRepository : AlbumRepository {
    val albumApi = App.retrofit.create(AlbumApi::class.java)

    override fun getAlbumList(): Observable<List<Album>> = albumApi.getAlbumList()

    override fun getPhotoList(albumId: Int): Observable<List<Photo>> = albumApi.getPhotoList(albumId)
}