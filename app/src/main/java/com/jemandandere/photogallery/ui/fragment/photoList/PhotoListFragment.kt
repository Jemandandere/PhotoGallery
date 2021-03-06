package com.jemandandere.photogallery.ui.fragment.photoList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.databinding.AlbumListRemoteFragmentBinding
import com.jemandandere.photogallery.databinding.PhotoListFragmentBinding
import com.jemandandere.photogallery.ui.fragment.albumList.remote.AlbumListRemoteViewModel
import com.jemandandere.photogallery.util.Constants

class PhotoListFragment : Fragment(R.layout.photo_list_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(PhotoListViewModel::class.java)
        val binding = PhotoListFragmentBinding.bind(view)
        binding.photoListLabel.text = arguments?.getInt(Constants.ALBUM_ID_KEY).toString()
    }
}