package com.example.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
        Log.d("testLogs", "application is closed")
    }
}