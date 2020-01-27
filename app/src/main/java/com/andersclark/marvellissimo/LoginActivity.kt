package com.andersclark.marvellissimo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.andersclark.marvellissimo.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.title = "Log in"
        login_button.setOnClickListener {

            val email = login_email.text.toString()
            val password = login_password.text.toString()

            Log.d("LoginActivity", "EMAIL: $email")
            Log.d("LoginActivity", "PASSWORD: $password")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    Log.d("LoginActivity", "Login complete")
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show()
                    intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Log.d("LoginActivity", "Failed to log in: ${it.message}")
                    Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }
        login_returntoregistration.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            //finish()
        }
    }
}
