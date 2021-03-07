package com.jemandandere.photogallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jemandandere.photogallery.R
import com.jemandandere.photogallery.model.Photo
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
                .placeholder(R.drawable.placeholder_load)
                .error(R.drawable.placeholder_no_image)
                .into(image)

            itemView.setOnClickListener {
                onClick(photo)
            }
        }
    }
}