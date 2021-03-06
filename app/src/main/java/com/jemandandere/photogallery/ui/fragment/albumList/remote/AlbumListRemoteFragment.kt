package com.jemandandere.photogallery.ui.fragment.albumList.remote

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.databinding.AlbumListRemoteFragmentBinding

class AlbumListRemoteFragment : Fragment(R.layout.album_list_remote_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(AlbumListRemoteViewModel::class.java)
        val binding = AlbumListRemoteFragmentBinding.bind(view)
    }
}