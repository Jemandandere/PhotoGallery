package com.jemandandere.photogallery.ui.fragment.albumList.local

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.adapter.AlbumListAdapter
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.databinding.AlbumListFragmentBinding
import com.jemandandere.photogallery.util.Constants

class AlbumListLocalFragment : Fragment(R.layout.album_list_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(AlbumListLocalViewModel::class.java)
        val binding = AlbumListFragmentBinding.bind(view)
        val recyclerView = binding.albumRecycler
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = AlbumListAdapter {
            findNavController().navigate(
                R.id.action_albumListLocalFragment_to_photoListFragment, bundleOf(
                    Constants.ALBUM_KEY to it
                )
            )
        }
        viewModel.albumList.observe(viewLifecycleOwner) {
            (recyclerView.adapter as AlbumListAdapter).updateData(it)
        }
        viewModel.updateAlbumList()
    }

}