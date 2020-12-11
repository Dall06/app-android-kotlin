package com.example.artister.helpers

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    private val PREFS_NAME = "com.cursokotlin.sharedpreferences"
    private val SHARED_id = "shared_id"
    private val SHARED_name = "shared_name"
    private val SHARED_email = "shared_email"
    private val SHARED_phone = "shared_phone"
    private val SHARED_bio = "shared_bio"
    private val SHARED_img = "shared_img"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var _id: String?
        get() = prefs.getString(SHARED_id, "")
        set(value) = prefs.edit().putString(SHARED_id, value).apply()

    var accountName: String?
        get() = prefs.getString(SHARED_name, "")
        set(value) = prefs.edit().putString(SHARED_name, value).apply()

    var email: String?
        get() = prefs.getString(SHARED_email, "")
        set(value) = prefs.edit().putString(SHARED_email, value).apply()

    var phone: String?
        get() = prefs.getString(SHARED_phone, "")
        set(value) = prefs.edit().putString(SHARED_phone, value).apply()

    var bio: String?
        get() = prefs.getString(SHARED_bio, "")
        set(value) = prefs.edit().putString(SHARED_bio, value).apply()

    var img: String?
        get() = prefs.getString(SHARED_img, "")
        set(value) = prefs.edit().putString(SHARED_img, value).apply()

}