package com.jemandandere.photogallery.ui.fragment.photoList

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jemandandere.photogallery.data.DataSource
import com.jemandandere.photogallery.data.local.DataLocalRepository
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import com.jemandandere.photogallery.data.remote.DataRemoteRepository
import com.jemandandere.photogallery.logic.DataService
import com.jemandandere.photogallery.util.Constants
import io.reactivex.disposables.CompositeDisposable

class PhotoListViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var album: Album
    val alreadySave: MutableLiveData<Boolean> = MutableLiveData()
    val photoList: MutableLiveData<List<Photo>> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()
    private lateinit var dataService: DataService

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun setDataSource(dataSource: DataSource) {
        dataService = DataService(
            if (dataSource == DataSource.LOCAL) DataLocalRepository(getApplication()) else DataRemoteRepository(),
            DataLocalRepository(getApplication())
        )
    }

    fun updatePhotoList(album: Album) {
        this.album = album
        compositeDisposable.add(
            dataService.loadPhotoList(album).subscribe({
                photoList.value = it
            }, {
                Log.d(Constants.TAG, it.localizedMessage ?: Constants.ERROR)
            }))
    }

    fun alreadySavedCheck() {
        dataService.check(album).subscribe({
            alreadySave.value = it
        }, {
            Log.d(Constants.TAG, it.localizedMessage ?: Constants.ERROR)
        })
    }

    fun save() {
        //TODO Херня какая-то
        dataService.saveAlbum(album, photoList.value!!).subscribe({
            alreadySave.value = true
            Toast.makeText(getApplication(), "Альбом успешно сохранён", Toast.LENGTH_SHORT).show()
        }, {
            Log.d(Constants.TAG, it.localizedMessage ?: Constants.ERROR)
        })
    }

    fun delete() {
        dataService.deleteAlbum(album, photoList.value!!).subscribe({
            alreadySave.value = false
            Toast.makeText(getApplication(), "Альбом удалён", Toast.LENGTH_SHORT).show()
        }, {
            Log.d(Constants.TAG, it.localizedMessage ?: Constants.ERROR)
        })
    }
}