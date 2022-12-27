package com.example.moviesapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ListItemBinding
import com.example.moviesapp.model.Artist
import javax.inject.Inject

class AdapterArtist @Inject constructor(context: Context?, artistListItem: ArrayList<Artist>) :
    RecyclerView.Adapter<AdapterArtist.ArtistViewHolder>() {
    private lateinit var context: Context
    private var artistListItem: ArrayList<Artist>
    var onItemClick:((Artist)->Unit)? = null

    init {

        if (context != null) {
            this.context = context
        }

        this.artistListItem = artistListItem
    }

    @SuppressLint("NotifyDataSetChanged", "LogNotTimber")
    fun updateList(artistListItem: List<Artist>) {
        this.artistListItem = artistListItem as ArrayList<Artist>
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ArtistViewHolder {
        return ArtistViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {

        val posterURL = "https://image.tmdb.org/t/p/w500" + artistListItem[position].profile
        Glide.with(context).load(posterURL).into(holder.binding.imageView)
        holder.binding.txtTitle.text = artistListItem[position].name
        holder.binding.txtOverview.text = artistListItem[position].name
        holder.itemView.setOnClickListener{
            onItemClick!!.invoke(artistListItem[position])
        }
    }

    override fun getItemCount(): Int {
        return artistListItem.size
    }

    inner class ArtistViewHolder(var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}