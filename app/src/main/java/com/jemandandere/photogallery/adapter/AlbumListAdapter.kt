package com.jemandandere.photogallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.data.model.Album
import com.jemandandere.photogallery.databinding.AlbumItemBinding

class AlbumListAdapter(private val onClick: (Album) -> Unit) : RecyclerView.Adapter<AlbumListAdapter.AlbumHolder>() {

    private var albumList = emptyList<Album>()

    fun updateData(albumList: List<Album>) {
        this.albumList = albumList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false)
        return AlbumHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        holder.bind(albumList[position], onClick)
    }

    override fun getItemCount(): Int = albumList.size

    class AlbumHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var album: Album

        private val binding = AlbumItemBinding.bind(view)
        private val title: TextView = binding.albumItemTitle

        fun bind(album: Album, onClick: (Album) -> Unit) {
            this.album = album
            this.title.text = album.title
            itemView.setOnClickListener {
                onClick(album)
            }
        }
    }
}