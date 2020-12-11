package com.example.artister.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artister.R
import com.example.artister.converters.ImageConverter
import com.example.artister.models.User
import kotlinx.android.synthetic.main.row_artist_layout.view.*
import kotlinx.android.synthetic.main.row_media_layout.view.*

public class RecyclerViewArtistAdapter:  RecyclerView.Adapter<RecyclerViewArtistAdapter.ArtistAdapterViewHolder>() {
    private var lstArtist = emptyList<User>()
    private val imageConverter = ImageConverter()
    inner class ArtistAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistAdapterViewHolder {
        return ArtistAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_artist_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return lstArtist.size
    }

    override fun onBindViewHolder(holder: ArtistAdapterViewHolder, i: Int) {

        var auxUserType: String = when (lstArtist[i].userType) {
            0 -> "Admin"
            1 -> "Artist"
            2 -> "Agency"
            else -> "Unknown"
        }

        if (lstArtist[i].img == "no_photo" || lstArtist[i].img == "" || lstArtist[i].img == "null") {
            holder.itemView.iv_artist_pic.setImageResource(R.drawable.no_img)
        } else {
            val bm: Bitmap? = lstArtist[i].img?.let { imageConverter.convertToBitmap(it) }
            holder.itemView.iv_artist_pic.setImageBitmap(bm)
        }

        holder.itemView.tv_artist_name.text = lstArtist[i].accountName
        holder.itemView.tv_user_type.text = auxUserType
    }

    fun setData(newList: List<User>) {
        lstArtist = newList
        notifyDataSetChanged()
    }
}