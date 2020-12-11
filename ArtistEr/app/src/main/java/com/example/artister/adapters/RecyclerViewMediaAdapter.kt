package com.example.artister.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.artister.R
import com.example.artister.converters.ImageConverter
import com.example.artister.models.Media
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.row_media_layout.view.*

public class RecyclerViewMediaAdapter: RecyclerView.Adapter<RecyclerViewMediaAdapter.MediaAdapterViewHolder>() {
    private var lstMedia = emptyList<Media>()
    private val imageConverter = ImageConverter()
    inner class MediaAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaAdapterViewHolder {
        return MediaAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_media_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return lstMedia.size
    }

    override fun onBindViewHolder(holder: MediaAdapterViewHolder, i: Int) {

        val auxCategory: String = when (lstMedia[i].category) {
            0 -> "Music"
            1 -> "Photo"
            2 -> "Film"
            3 -> "Design"
            4 -> "Tattoo"
            5 -> "Lecture"
            else -> "Unknown"
        }
        holder.itemView.setOnClickListener {

        }

        if (lstMedia[i].img == "no_photo" || lstMedia[i].img == "" || lstMedia[i].img == "null") {
            holder.itemView.iv_media.setImageResource(R.drawable.no_img)
        } else {
            val bm: Bitmap? = lstMedia[i].img?.let { imageConverter.convertToBitmap(it) }
            holder.itemView.iv_media.setImageBitmap(bm)
        }

        holder.itemView.tv_yt.text = lstMedia[i].links.youtubeChannel
        holder.itemView.tv_ig.text = lstMedia[i].links.instagramProfile
        holder.itemView.tv_description.text = lstMedia[i].description
        holder.itemView.tv_user.text = lstMedia[i].userName
        holder.itemView.tv_category.text = auxCategory
    }

    fun setData(newList: List<Media>) {
        lstMedia = newList
        notifyDataSetChanged()
    }
}