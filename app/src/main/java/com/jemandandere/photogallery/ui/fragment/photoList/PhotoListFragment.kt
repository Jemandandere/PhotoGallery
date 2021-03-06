package com.jemandandere.photogallery.ui.fragment.photoList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.adapter.PhotoListAdapter
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.data.model.Photo
import com.jemandandere.photogallery.databinding.PhotoListFragmentBinding
import com.jemandandere.photogallery.util.Constants

class PhotoListFragment : Fragment(R.layout.photo_list_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(PhotoListViewModel::class.java)
        val binding = PhotoListFragmentBinding.bind(view)
        val album : Album = arguments?.getSerializable(Constants.ALBUM_KEY) as Album
        val recyclerView = binding.photoRecycler
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = PhotoListAdapter{
            // TODO Change to fullscreen image view
            Log.d(Constants.TAG, " " + it.albumId + " " + it.id)
        }
        var observer = Observer<List<Photo>> {
            (recyclerView.adapter as PhotoListAdapter).updateData(it)
        }
        viewModel.photoList.observe(viewLifecycleOwner, observer)
        viewModel.updatePhotoList(album.id)
    }
}