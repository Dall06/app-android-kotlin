package com.example.artister.api

import com.example.artister.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {
    @GET("/users/{userid}")
    fun  getUserById(
        @Path("userid") _id: String
    ): Call<User>

    @GET("/usersbytype/{userType}")
    fun getUsersByType(
        @Path("userType") userType: Int
    ) :  Call<List<User>>

    @POST("/users")
    fun insertUser(
        @Body user: User
    ) : Call<ApiResponse>

    @PUT("/user_update/{userid}")
    fun updateUser(
        @Path("userid") userid: String,
        @Body user: User
    ) : Call<ApiResponse>

    @POST("/users/login")
    fun loginUser(
        @Body user: User
    ) : Call<ApiResponseLogin>
}