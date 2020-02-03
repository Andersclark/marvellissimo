package com.andersclark.marvellissimo

import android.app.Application
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.realm.Realm

private const val TAG = "MyApplication"
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        verifyUserIsLoggedIn()
    }
    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}