package com.jemandandere.photogallery.logic

import com.jemandandere.photogallery.data.DataRepository
import com.jemandandere.photogallery.model.Album
import com.jemandandere.photogallery.model.Photo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataService(
    private val dataRepository: DataRepository
) {
    fun loadAlbumList(): Single<List<Album>> {
        return dataRepository.getAlbumList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cache()
    }

    fun loadPhotoList(album: Album): Single<List<Photo>> {
        return dataRepository.getPhotoList(album)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cache()
    }
}