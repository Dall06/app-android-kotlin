package com.example.artister.communicators

import com.example.artister.models.User

interface Communicator {
    fun passData(user: User)
    fun toReturn()
}