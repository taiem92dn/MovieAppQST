package com.tngdev.movieappqst.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tngdev.movieappqst.databinding.ItemMovieListBinding
import com.tngdev.movieappqst.model.MovieItem

class MovieListAdapter : ListAdapter<MovieItem, ViewHolder>(DIFF_CALLBACK) {

    var onItemClickListener: (movieItem: MovieItem) -> Unit? = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMovieListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }


    inner class ViewHolder(val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { movieItem ->
                    onItemClickListener(movieItem)
                }
            }
        }

        fun bind(item: MovieItem) {
            binding.also {
                it.movieItem = item
                it.executePendingBindings()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(
                oldItem: MovieItem, newItem: MovieItem
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: MovieItem, newItem: MovieItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
