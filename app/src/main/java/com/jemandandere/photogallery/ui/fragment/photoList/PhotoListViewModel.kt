package com.jemandandere.photogallery.ui.fragment.photoList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jemandandere.photogallery.data.model.Photo
import com.jemandandere.photogallery.data.remote.DataRemoteRepository
import com.jemandandere.photogallery.logic.DataService
import com.jemandandere.photogallery.util.Constants
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PhotoListViewModel : ViewModel() {
    val photoList: MutableLiveData<List<Photo>> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()
    private val dataService = DataService(DataRemoteRepository())

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun updatePhotoList(albumId: Int) {
        compositeDisposable.add(
            dataService.loadPhotoList(albumId).subscribe({
                photoList.value = it
            }, {
                Log.d(Constants.TAG, it.localizedMessage ?: Constants.ERROR)
            }))
    }
}