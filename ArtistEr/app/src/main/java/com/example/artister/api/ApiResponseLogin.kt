package com.example.artister.api

import com.example.artister.models.Links
import com.example.artister.models.User

val links = Links()

data class ApiResponseLogin(
    var status: String = "",
    var message: String = "",
    var body: User = User(links = links)
)