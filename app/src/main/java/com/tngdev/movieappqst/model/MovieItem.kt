package com.tngdev.movieappqst.model

import android.os.Parcelable
import androidx.annotation.IdRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieItem(
    @IdRes
    val imageRes: Int,
    val title: String,
    val description: String,
    val rating: Float,
    val duration: String,
    val genre: String,
    val releasedDate: String,
    val trailerLink: String
): Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieItem

        if (!title.contentEquals(other.title)) return false

        return true
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }
}
