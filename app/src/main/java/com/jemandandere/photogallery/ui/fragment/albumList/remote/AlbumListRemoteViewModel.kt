package com.jemandandere.photogallery.ui.fragment.albumList.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.remote.DataRemoteRepository
import com.jemandandere.photogallery.logic.DataService
import com.jemandandere.photogallery.util.Constants
import io.reactivex.rxjava3.disposables.CompositeDisposable

class AlbumListRemoteViewModel : ViewModel() {
    val albumList: MutableLiveData<List<Album>> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()
    private val dataService = DataService(DataRemoteRepository())

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
            }))
    }
}