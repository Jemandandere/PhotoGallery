package com.jemandandere.photogallery.ui.fragment.albumList.local

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jemandandere.photogallery.data.local.DataLocalRepository
import com.jemandandere.photogallery.model.Album
import com.jemandandere.photogallery.logic.DataService
import com.jemandandere.photogallery.util.Constants
import io.reactivex.disposables.CompositeDisposable

class AlbumListLocalViewModel(application: Application) : AndroidViewModel(application) {
    val albumList: MutableLiveData<List<Album>> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()
    private val dataService = DataService(DataLocalRepository(application))

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun updateAlbumList() {
        compositeDisposable.add(
            dataService.loadAlbumList().subscribe({
                albumList.value = it
            }, {
                Log.d(Constants.TAG, it.localizedMessage ?: Constants.ERROR)
            })
        )
    }
}