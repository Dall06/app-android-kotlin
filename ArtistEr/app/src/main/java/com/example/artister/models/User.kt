package com.example.artister.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var _id: String = "",
    var accountName: String = "",
    var password: String = "",
    var email: String = "",
    var phone: String = "",
    var bio: String = "",
    var userType: Int = 0,
    var img: String = "",
    var links: Links
) : Parcelable
@Parcelize
data class Links (
    var webPage: String = "",
    var facebookPage: String = "",
    var youtubeChannel: String = "",
    var spotifyAccount: String = "",
    var instagramProfile: String = ""
) : Parcelable