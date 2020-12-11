package com.example.artister.controllers

import android.content.Context
import com.example.artister.api.*
import com.example.artister.models.User
import retrofit2.Call

class UserController {
    fun loginUser (user: User): Call<ApiResponseLogin> {
        val userApiService: UserApiService = RestEngine.getRestEngine().create(UserApiService::class.java)

        return userApiService.loginUser(user)
    }

    fun getUsersByType(type: Int): Call<List<User>> {

        val userApiService: UserApiService = RestEngine.getRestEngine().create(UserApiService::class.java)

        return userApiService.getUsersByType(type)
    }

    fun getUseById(id: String): Call<User> {

        val userApiService: UserApiService = RestEngine.getRestEngine().create(UserApiService::class.java)

        return userApiService.getUserById(id)
    }

    fun updateUser(id: String, user: User): Call<ApiResponse> {

        val userApiService: UserApiService = RestEngine.getRestEngine().create(UserApiService::class.java)

        return userApiService.updateUser(id, user)
    }

    fun addUser(user: User): Call<ApiResponse> {
        val userApiService: UserApiService = RestEngine.getRestEngine().create(UserApiService::class.java)

        return userApiService.insertUser(user)
    }
}