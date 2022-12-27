package com.example.moviesapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ListItemBinding
import com.example.moviesapp.di.GlideApp
import com.example.moviesapp.model.TvShow
import javax.inject.Inject

class AdapterTv @Inject constructor(
    context: Context?,
    tvListItem: ArrayList<TvShow>
) :
    RecyclerView.Adapter<AdapterTv.TvViewHolder>() {
    private lateinit var context: Context
    private var tvListItem: ArrayList<TvShow>
    var onItemClick :((TvShow)->Unit)? = null

    /**
     * constructor contextApp And listTvItem
     */
    init {
        if (context != null) {
            this.context = context
        }
        this.tvListItem = tvListItem
    }

    /**
     * function update data if connect Api getData
     */
    @SuppressLint("NotifyDataSetChanged")
    fun updateListTv(tvListItem: List<TvShow>) {
        this.tvListItem = tvListItem as ArrayList<TvShow>
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TvViewHolder {
        return TvViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * view data Image tvListItem && Name && Overview
     */

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val posterUrl = "https://image.tmdb.org/t/p/w500" + tvListItem[position].posterPath
        GlideApp.with(context).load(posterUrl).into(holder.binding.imageView)
        holder.binding.txtTitle.text = tvListItem[position].name
        holder.binding.txtOverview.text = tvListItem[position].overview
        holder.itemView.setOnClickListener{
            onItemClick!!.invoke(tvListItem[position])
        }
    }

    /**
     * get Size Adapter
     */
    override fun getItemCount(): Int {
        if (tvListItem == null) {
            return 0
        } else {
            return tvListItem.size
        }
    }

    class TvViewHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}