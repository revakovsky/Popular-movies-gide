package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(  //создали объект авторизации экрана
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)  // запуск самого экрана авторизации
    }

    private lateinit var database: DatabaseReference  //создали объект для записи в базу данных

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentToAnotherScreen = Intent(this, MoviesActivity::class.java)
        startActivity(intentToAnotherScreen)
        Log.d("testLogs", "in onCreate")

        database = Firebase.database.reference  //инициализация базы данных через загруженную библиотеку

        val providers = arrayListOf(  //создаем список с возможными вариантами регистрации пользователя
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
            )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()  //создали интент и передали ему список с вариантами регистрации
        signInLauncher.launch(signInIntent) //запустили экран firebase auth

    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse  //получаем результат с экрана firebase auth
        if (result.resultCode == RESULT_OK) {  //если результат ОК
            Log.d("testLogs", "registration activity: successes ${response?.email}")

            // Successfully signed in
            val authenticatedUser = FirebaseAuth.getInstance().currentUser  //создаем объект текущего пользователя на основе firebase auth
            authenticatedUser?.let {  //и если этот объект существует - то мы сохраняем его в базу данных
                val email = it.email.toString()  //извлекаем email нашего пользователя из записи в базе данных
                val uid = it.uid  //извлекаем uid нашего пользователя из записи в базе данных
                val firebaseUser = User(email, uid)  //создаем новый объект User на основании созданного в программе класса
                database.child("users").child(uid).setValue(firebaseUser) //сохраняем нашего пользователя в базу данных firebase realtime
            }

        } else {  //если результат не ОК должны обработать ошибку
            Log.d("testLogs", "registration activity: failure")
            Toast.makeText(this@MainActivity, "something wrong with registration!", Toast.LENGTH_SHORT).show()
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        this.finishAffinity()
//        Log.d("testLogs", "application is closed")
//    }
    
}
