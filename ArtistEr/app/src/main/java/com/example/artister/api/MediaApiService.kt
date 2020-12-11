package com.example.artister.api

import com.example.artister.models.Media
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MediaApiService {
    @GET("/media")
    fun getMedia() : Call<List<Media>>

    @GET("/media/{mediaid}")
    fun  getMediaById(
        @Path("mediaid") _id: String
    ): Call<Media>

    @GET("/media/{artist}")
    fun getMediaByArtist(
        @Path("artist") artist: String
    ) : Call<List<Media>>

    @POST("/media")
    fun addMedia(
        @Body media: Media
    ) : Call<ApiResponse>

    @PUT("/media/{mediaid}")
    fun updateMedia(
        @Path ("mediaid") mediaid: String,
        @Body media: Media
    ) : Call<ApiResponse>

    @DELETE("/media/{mediaid}")
    fun deleteMedia(
        @Path ("mediaid") mediaid: String
    ) : Call<ApiResponse>
}