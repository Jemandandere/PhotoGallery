package com.jemandandere.photogallery.ui.fragment.albumList.remote

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.adapter.AlbumListAdapter
import com.jemandandere.photogallery.databinding.AlbumListFragmentBinding
import com.jemandandere.photogallery.util.Constants

class AlbumListRemoteFragment : Fragment(R.layout.album_list_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(AlbumListRemoteViewModel::class.java)
        val binding = AlbumListFragmentBinding.bind(view)
        val recyclerView = binding.albumRecycler
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = AlbumListAdapter {
            findNavController().navigate(
                R.id.action_albumListRemoteFragment_to_photoListFragment, bundleOf(
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