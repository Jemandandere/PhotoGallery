package com.jemandandere.photogallery.ui.fragment.photoList

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jemandandere.photogallery.data.local.DataLocalRepository
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import com.jemandandere.photogallery.data.remote.DataRemoteRepository
import com.jemandandere.photogallery.logic.DataService
import com.jemandandere.photogallery.logic.PrivateService
import com.jemandandere.photogallery.util.Constants
import io.reactivex.disposables.CompositeDisposable

class PhotoListViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var album: Album
    private val photoList
        get() = photoListLiveData.value!!
    val photoListLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
    val alreadySave: MutableLiveData<Boolean> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()
    private lateinit var dataService: DataService
    private var privateService = PrivateService(DataLocalRepository(application))

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    // Проверяем, есть ли альбом на устройстве
    // + Грузим его с устройства, отключаем сохранение
    // - Грузим его с веба, отключаем удаление
    // При сохранении/удалении можем менять источник данных и перезагружать список,
    //      но задание не подразумевает различий между содержимым, поэтому обновление смысла не имеет

    fun checkAlbum(album: Album) {
        this.album = album
        compositeDisposable.add(
            privateService.isSave(album).subscribe({
                if (it) {
                    dataService = DataService(DataLocalRepository(getApplication()))
                } else {
                    dataService = DataService(DataRemoteRepository())
                }
                updatePhotoList(album)
                alreadySave.value = it
            }, {
                Log.d(Constants.TAG, it.localizedMessage ?: Constants.ERROR)
            })
        )
    }

    private fun updatePhotoList(album: Album) {
        compositeDisposable.add(
            dataService.loadPhotoList(album).subscribe({
                photoListLiveData.value = it
            }, {
                Log.d(Constants.TAG, it.localizedMessage ?: Constants.ERROR)
            })
        )
    }

    fun save() {
        compositeDisposable.add(
            privateService.saveAlbum(album, photoList).subscribe({
                alreadySave.value = true
                Toast.makeText(getApplication(), "Альбом успешно сохранён", Toast.LENGTH_SHORT)
                    .show()
            }, {
                Log.d(Constants.TAG, it.localizedMessage ?: Constants.ERROR)
            })
        )
    }

    fun delete() {
        compositeDisposable.add(
            privateService.deleteAlbum(album, photoList).subscribe({
                alreadySave.value = false
                Toast.makeText(getApplication(), "Альбом удалён", Toast.LENGTH_SHORT).show()
            }, {
                Log.d(Constants.TAG, it.localizedMessage ?: Constants.ERROR)
            })
        )
    }

}