package com.jemandandere.photogallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.data.model.Photo
import com.jemandandere.photogallery.databinding.PhotoItemBinding
import com.squareup.picasso.Picasso

class PhotoListAdapter(private val onClick: (Photo) -> Unit) : RecyclerView.Adapter<PhotoListAdapter.PhotoHolder>() {

    private var photoList = emptyList<Photo>()

    fun updateData(photoList: List<Photo>) {
        this.photoList = photoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(photoList[position], onClick)
    }

    override fun getItemCount(): Int = photoList.size

    class PhotoHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var photo: Photo

        private val binding = PhotoItemBinding.bind(view)
        private val title: TextView = binding.photoItemTitle
        private val image: ImageView = binding.photoItemImage

        fun bind(photo: Photo, onClick: (Photo) -> Unit) {
            this.photo = photo
            this.title.text = photo.title

            Picasso.get().load(photo.thumbnailUrl)
                .into(image)

            itemView.setOnClickListener {
                onClick(photo)
            }
        }
    }
}

/*

class AlbumListAdapter(val onClick: (Album) -> Unit) : RecyclerView.Adapter<AlbumListAdapter.AlbumListHolder>() {

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
        holder.bind(albumList[position], onClick)
    }

    override fun getItemCount(): Int = albumList.size

    class AlbumListHolder(view: View) : RecyclerView.ViewHolder(view) {

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

 */