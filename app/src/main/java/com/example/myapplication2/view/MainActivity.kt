package com.example.myapplication2.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.R
import com.example.myapplication2.data.User
import com.example.myapplication2.viewmodel.MainActivityViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val mMainActivityViewModel : MainActivityViewModel = MainActivityViewModel()

    private val signInLauncher = registerForActivityResult(  //создали объект авторизации экрана
        FirebaseAuthUIActivityResultContract()
    ) { resultCallback ->
        this.onSignInResult(resultCallback)  // запуск самого экрана авторизации
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openRegistrationScreen()
    }

    /**
    * We make a call to the firebase auth api to show a dialog for registration
    * */
    private fun openRegistrationScreen() {
        val intentToAnotherScreen = Intent(this, MoviesActivity::class.java)
        startActivity(intentToAnotherScreen)
        Log.d("testLogs", "in onCreate")

        val providers = arrayListOf(  //создаем список с возможными вариантами регистрации пользователя
            AuthUI.IdpConfig.EmailBuilder().build(),
//            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()  //создали интент и передали ему список с вариантами регистрации
        signInLauncher.launch(signInIntent) //запустили экран firebase auth
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        //val response = result.idpResponse  //получаем результат с экрана firebase auth
        when (result.resultCode) {  //если результат ОК
            RESULT_OK -> {
                Log.d("testLogs", "registration activity: successes ${result.resultCode}")

                // Successfully signed in
                val authenticatedUser = FirebaseAuth.getInstance().currentUser  //создаем объект текущего пользователя на основе firebase auth
                authenticatedUser?.let {  //и если этот объект существует - то мы сохраняем его в базу данных
                    val email = it.email.toString()  //извлекаем email нашего пользователя из записи в базе данных
                    val uid = it.uid  //извлекаем uid нашего пользователя из записи в базе данных
                    val firebaseUser = User(email, uid)  //создаем новый объект User на основании созданного в программе класса

                    mMainActivityViewModel.updateUserData(firebaseUser, uid)

                    val intentToAnotherScreen = Intent(this, MoviesActivity::class.java)
                    startActivity(intentToAnotherScreen)
                    Log.d("testLogs", "in onCreate")
                }
            }
            RESULT_CANCELED -> {
                //если результат не ОК должны обработать ошибку
                Log.d("testLogs", "registration activity: failure")
                Toast.makeText(this@MainActivity, "something wrong with registration!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // do not anything
            }
        }
    }
}
