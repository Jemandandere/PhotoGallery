package com.jemandandere.photogallery.ui.fragment.photoList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.adapter.PhotoListAdapter
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.databinding.PhotoListFragmentBinding
import com.jemandandere.photogallery.util.Constants

class PhotoListFragment : Fragment(R.layout.photo_list_fragment) {

    private lateinit var viewModel: PhotoListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val binding = PhotoListFragmentBinding.bind(view)
        val album: Album = arguments?.getSerializable(Constants.ALBUM_KEY) as Album
        val recyclerView = binding.photoRecycler
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = PhotoListAdapter {
            // TODO Change to fullscreen image view
            Log.d(Constants.TAG, "" + it.albumId + " " + it.id)
        }
        viewModel = ViewModelProvider(this).get(PhotoListViewModel::class.java)
        viewModel.photoListLiveData.observe(viewLifecycleOwner) {
            (recyclerView.adapter as PhotoListAdapter).updateData(it)
        }
        // Когда объект сохраняется/удаляется, нужно менять доступные пункты меню
        viewModel.alreadySave.observe(viewLifecycleOwner) {
            requireActivity().invalidateOptionsMenu()
        }
        viewModel.checkAlbum(album)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.photo_list_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (viewModel.alreadySave.value != null) {
            if (viewModel.alreadySave.value == true) {
                menu.removeItem(R.id.photo_list_menu_save)
            } else {
                menu.removeItem(R.id.photo_list_menu_delete)
            }
        } else {
            // На случай фризов или проблем с базой, в самом начале не отображаем ничего
            menu.removeItem(R.id.photo_list_menu_save)
            menu.removeItem(R.id.photo_list_menu_delete)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.photo_list_menu_save -> viewModel.save()
            R.id.photo_list_menu_delete -> viewModel.delete()
        }
        return super.onOptionsItemSelected(item)
    }
}