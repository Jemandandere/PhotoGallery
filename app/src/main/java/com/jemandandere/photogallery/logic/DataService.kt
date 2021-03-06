package com.jemandandere.photogallery.logic

import com.jemandandere.photogallery.data.DataRepository
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class DataService(private val repository: DataRepository) {
    fun loadAlbumList(): Observable<List<Album>> {
        return repository.getAlbumList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cache()
    }

    fun loadPhotoList(albumId: Int): Observable<List<Photo>> {
        return repository.getPhotoList(albumId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cache()
    }
}