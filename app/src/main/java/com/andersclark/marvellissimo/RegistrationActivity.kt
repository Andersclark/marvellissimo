package com.andersclark.marvellissimo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.andersclark.marvellissimo.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

private const val TAG = "RegistrationActivity"
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.title = "Register"
        registration_registerbutton.setOnClickListener {
            performRegister()
        }
       registration_alreadyhaveaccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performRegister() {
        val email = registration_email.text.toString()
        val password = registration_password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter a viable email and password", Toast.LENGTH_SHORT)
                .show()
            return
        }
        Log.d(TAG, "Email is: ${email}")
        Log.d(TAG, "Password is: ${password}")
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d(
                    TAG,
                    "SUCCESSFULLY CREATED USER with UID: ${it.result!!.user?.uid}"
                )
                saveUserToFireBaseDatabase()
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_LONG)
                    .show()
            }
    }
    private fun saveUserToFireBaseDatabase() {
        Toast.makeText(this, "Registering user...", Toast.LENGTH_SHORT)
            .show()
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(
            uid,
            registration_username.text.toString()
        )
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "User ${user.username} saved to Firebase")
                Toast.makeText(this, "Logged in as ${registration_username.text}", Toast.LENGTH_SHORT).show()
               val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }
}
