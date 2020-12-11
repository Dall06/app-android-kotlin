package com.example.artister.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media (
    var _id: String = "",
    var description: String = "",
    var userId: String = "",
    var userName: String = "",
    var category: Int = 0,
    var img: String = "",
    var links: LinksMedia
): Parcelable

@Parcelize
data class LinksMedia (
    var webPage: String = "",
    var facebookPage: String = "",
    var youtubeChannel: String = "",
    var spotifyAccount: String = "",
    var instagramProfile: String = ""
) : Parcelable
