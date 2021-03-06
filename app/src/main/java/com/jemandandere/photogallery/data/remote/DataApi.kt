package com.jemandandere.photogallery.data.remote

import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import com.jemandandere.photogallery.util.Constants
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DataApi {
    @GET(Constants.ALBUM_LIST_PATH)
    fun getAlbumList(): Observable<List<Album>>

    @GET(Constants.PHOTO_LIST_PATH)
    fun getPhotoList(@Query("albumId") albumId: Int): Observable<List<Photo>>
}