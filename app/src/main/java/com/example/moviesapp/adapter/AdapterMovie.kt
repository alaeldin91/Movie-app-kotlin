package com.example.moviesapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ListItemBinding
import com.example.moviesapp.di.GlideApp
import com.example.moviesapp.model.Movie
import javax.inject.Inject

class AdapterMovie @Inject constructor(context: Context?, movieListItem: ArrayList<Movie>) :
    RecyclerView.Adapter<AdapterMovie.MovieViewHolder>() {
    private lateinit var context: Context
    private var movieListItem = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    init {
        if (context != null) {
            this.context = context
        }
        this.movieListItem = movieListItem

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(movieListItem: List<Movie>) {
        this.movieListItem = movieListItem as ArrayList<Movie>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterMovie.MovieViewHolder {
        return MovieViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AdapterMovie.MovieViewHolder, position: Int) {
        val posterURL = "https://image.tmdb.org/t/p/w500" + movieListItem[position].posterPath
        GlideApp.with(context).load(posterURL)
            .into(holder.binding.imageView)
        holder.binding.txtTitle.text = movieListItem[position].title
        holder.binding.txtOverview.text = movieListItem[position].overview
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(movieListItem[position])
        }
    }


    override fun getItemCount(): Int {
        return if (movieListItem == null) {
            0
        } else {
            movieListItem.size
        }

    }

    inner class MovieViewHolder(var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}
