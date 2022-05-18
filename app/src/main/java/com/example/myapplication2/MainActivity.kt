package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("testLogs", "in onCreate")
        database = Firebase.database.reference



        // get reference to button
        val registerButton = findViewById<Button>(R.id.main_activity_button_register)

        // set on-click listener
        registerButton.setOnClickListener {
            Log.d("testLogs", "click")
            val intentToRegistrationActivity = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intentToRegistrationActivity)

            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("testLogs", "is onStop")
    }
}
