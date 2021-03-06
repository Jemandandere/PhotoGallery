package com.jemandandere.photogallery.ui.fragment.albumList.local

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.databinding.AlbumListLocalFragmentBinding

class AlbumListLocalFragment : Fragment(R.layout.album_list_local_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(AlbumListLocalViewModel::class.java)
        val binding = AlbumListLocalFragmentBinding.bind(view)
    }

}