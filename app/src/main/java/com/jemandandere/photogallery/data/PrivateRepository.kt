package com.jemandandere.photogallery.data

import com.jemandandere.photogallery.model.Album
import com.jemandandere.photogallery.model.Photo
import io.reactivex.Completable
import io.reactivex.Single

interface PrivateRepository {
    fun isSave(album: Album) : Single<Boolean>

    fun insert(album: Album, photoList: List<Photo>) : Completable

    fun delete(album: Album, photoList: List<Photo>) : Completable
}