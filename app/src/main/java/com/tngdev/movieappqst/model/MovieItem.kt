package com.tngdev.movieappqst.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieItem(
    @DrawableRes
    var imageRes: Int,
    val title: String,
    val description: String,
    val rating: Float,
    val duration: String,
    val genre: String,
    @SerializedName("released_date")
    val releasedDate: String,
    @SerializedName("trailer_link")
    val trailerLink: String,
    var isOnMyWatchList: Boolean,
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
