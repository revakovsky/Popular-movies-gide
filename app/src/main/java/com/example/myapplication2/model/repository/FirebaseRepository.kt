package com.example.myapplication2.model.repository

import com.example.myapplication2.data.User

interface FirebaseRepository {
    fun updateUserData(firebaseUser: User, uId: String)
}