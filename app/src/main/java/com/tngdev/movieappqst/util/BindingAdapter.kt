package com.tngdev.movieappqst.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tngdev.movieappqst.model.MovieItem
import java.util.*

@BindingAdapter("movieSubtitle")
fun setMovieSubtitle(textView: TextView, movieItem: MovieItem) {
    textView.text = String.format("${movieItem.duration} - ${movieItem.genre}")
}

@BindingAdapter("movieRate")
fun setMovieRate(textView: TextView, movieItem: MovieItem) {
    textView.text = String.format("${movieItem.rating}/10")
}

@BindingAdapter("movieImage")
fun loadMovieImage(imageView: ImageView, movieItem: MovieItem) {
//    imageView.setImageResource()
}