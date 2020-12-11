package com.example.artister.adapters

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.artister.R
import com.example.artister.converters.ImageConverter
import com.example.artister.models.User
import kotlinx.android.synthetic.main.row_agencies_layout.view.*
import kotlinx.android.synthetic.main.row_agencies_layout.view.tv_user_type
import kotlinx.android.synthetic.main.row_artist_layout.view.*
import kotlinx.android.synthetic.main.row_media_layout.view.*

public class RecyclerViewAgencyAdapter:  RecyclerView.Adapter<RecyclerViewAgencyAdapter.AgencyAdapterViewHolder>() {
    private var lstAgencies = emptyList<User>()
    private val imageConverter = ImageConverter()
    inner class AgencyAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgencyAdapterViewHolder {
        return AgencyAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_agencies_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return lstAgencies.size
    }

    override fun onBindViewHolder(holder: AgencyAdapterViewHolder, i: Int) {

        var auxUserType: String = when (lstAgencies[i].userType) {
            0 -> "Admin"
            1 -> "Artist"
            2 -> "Agency"
            else -> "Unknown"
        }

        if (lstAgencies[i].img == "no_photo" || lstAgencies[i].img == "" || lstAgencies[i].img == "null") {
            holder.itemView.iv_agencies_pic.setImageResource(R.drawable.no_img)
        } else {
            val bm: Bitmap? = lstAgencies[i].img?.let { imageConverter.convertToBitmap(it) }
            holder.itemView.iv_agencies_pic.setImageBitmap(bm)
        }

        holder.itemView.tv_user_name.text = lstAgencies[i].accountName
        holder.itemView.tv_email.text = lstAgencies[i].email
        holder.itemView.tv_user_type.text = auxUserType
    }

    fun setData(newList: List<User>) {
        lstAgencies = newList
        notifyDataSetChanged()
    }
}