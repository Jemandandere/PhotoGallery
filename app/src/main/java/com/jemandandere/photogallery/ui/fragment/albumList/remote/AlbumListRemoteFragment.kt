package com.jemandandere.photogallery.ui.fragment.albumList.remote

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.adapter.AlbumListAdapter
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.databinding.AlbumListRemoteFragmentBinding
import com.jemandandere.photogallery.util.Constants

class AlbumListRemoteFragment : Fragment(R.layout.album_list_remote_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(AlbumListRemoteViewModel::class.java)
        val binding = AlbumListRemoteFragmentBinding.bind(view)
        val recyclerView = binding.albumRecyclerRemote
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = AlbumListAdapter {
            findNavController().navigate(R.id.action_albumListRemoteFragment_to_photoListFragment, bundleOf(Constants.ALBUM_KEY to it))
        }
        var observer = Observer<List<Album>> {
            (recyclerView.adapter as AlbumListAdapter).updateData(it)
        }
        viewModel.albumList.observe(viewLifecycleOwner, observer)
        viewModel.updateAlbumList()
    }

}