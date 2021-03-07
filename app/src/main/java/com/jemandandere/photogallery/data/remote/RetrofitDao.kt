package com.jemandandere.photogallery.data.remote

import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import com.jemandandere.photogallery.util.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitDao {
    @GET(Constants.ALBUM_LIST_PATH)
    fun getAlbumList(): Observable<List<Album>>

    @GET(Constants.PHOTO_LIST_PATH)
    fun getPhotoList(@Query("albumId") albumId: Int): Observable<List<Photo>>
}