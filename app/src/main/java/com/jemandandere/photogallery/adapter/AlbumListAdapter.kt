package com.jemandandere.photogallery.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.databinding.AlbumItemBinding
import com.jemandandere.photogallery.util.Constants
import okio.blackholeSink

class AlbumListAdapter() : RecyclerView.Adapter<AlbumListAdapter.AlbumListHolder>() {

    private var albumList = emptyList<Album>()

    fun updateData(albumList: List<Album>) {
        this.albumList = albumList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false)
        return AlbumListHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumListHolder, position: Int) {
        holder.bind(albumList[position])
    }

    override fun getItemCount(): Int = albumList.size

    class AlbumListHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var album: Album

        private val binding = AlbumItemBinding.bind(view)
        private val title: TextView = binding.albumItemTitle

        fun bind(album: Album) {
            this.album = album
            this.title.text = album.title

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(Constants.ALBUM_ID_KEY, album.id)
                Navigation.findNavController(itemView).navigate(R.id.action_albumListRemoteFragment_to_photoListFragment, bundle)
            }
        }
    }
}