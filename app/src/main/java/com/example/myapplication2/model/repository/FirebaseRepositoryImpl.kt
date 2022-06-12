package com.example.myapplication2.model.repository

import com.example.myapplication2.data.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRepositoryImpl : FirebaseRepository{

    private var database: DatabaseReference = Firebase.database.reference  //создали базу данных через загруженную библиотеку для записи в базу данных

    override fun updateUserData(firebaseUser: User, uId: String) {
        database.child("users").child(uId).setValue(firebaseUser) //сохраняем нашего пользователя в базу данных firebase realtime

    }
}