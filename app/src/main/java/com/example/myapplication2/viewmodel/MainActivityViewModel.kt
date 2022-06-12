package com.example.myapplication2.viewmodel

import com.example.myapplication2.data.User
import com.example.myapplication2.model.repository.FirebaseRepository
import com.example.myapplication2.model.repository.FirebaseRepositoryImpl

class MainActivityViewModel {
    private val mFirebaseRepository : FirebaseRepository = FirebaseRepositoryImpl()

    fun updateUserData(firebaseUser: User, uId: String) {
        mFirebaseRepository.updateUserData(firebaseUser, uId)
    }
}