package com.jemandandere.photogallery.logic

import com.jemandandere.photogallery.data.PrivateRepository
import com.jemandandere.photogallery.model.Album
import com.jemandandere.photogallery.model.Photo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PrivateService(
    private val privateRepository: PrivateRepository
) {
    fun isSave(album: Album) : Single<Boolean> {
        return privateRepository.isSave(album)
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