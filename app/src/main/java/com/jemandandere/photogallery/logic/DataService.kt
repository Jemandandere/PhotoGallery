package com.jemandandere.photogallery.logic

import com.jemandandere.photogallery.data.DataRepository
import com.jemandandere.photogallery.data.PrivateRepository
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataService(
    private val dataRepository: DataRepository,
    private val privateRepository: PrivateRepository
) {
    fun loadAlbumList(): Observable<List<Album>> {
        return dataRepository.getAlbumList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cache()
    }

    fun loadPhotoList(album: Album): Observable<List<Photo>> {
        return dataRepository.getPhotoList(album)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cache()
    }

    fun check(album: Album) : Single<Boolean> {
        return privateRepository.check(album)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveAlbum(album: Album, photoList: List<Photo>): Completable {
        return privateRepository.insert(album, photoList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun deleteAlbum(album: Album, photoList: List<Photo>): Completable {
        return privateRepository.delete(album, photoList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}