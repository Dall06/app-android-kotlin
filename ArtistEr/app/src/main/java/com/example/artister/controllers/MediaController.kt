package com.example.artister.controllers

import com.example.artister.api.ApiResponse
import com.example.artister.api.MediaApiService
import com.example.artister.api.RestEngine
import com.example.artister.api.UserApiService
import com.example.artister.models.Media
import retrofit2.Call
import retrofit2.Response

class MediaController {
    fun getAllMedia(): Call<List<Media>> {
        val mediaApiService: MediaApiService = RestEngine.getRestEngine().create(MediaApiService::class.java)

        return mediaApiService.getMedia()
    }

    fun addMedia(media: Media): Call<ApiResponse> {
        val mediaApiService: MediaApiService = RestEngine.getRestEngine().create(MediaApiService::class.java)

        return mediaApiService.addMedia(media)
    }
}