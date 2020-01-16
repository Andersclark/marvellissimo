package com.andersclark.marvellissimo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.andersclark.marvellissimo.services.MarvelClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MarvelClient.marvelService.getSomeCharacters(limit=7)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null)
                    Log.d(TAG, "FAIL :( getAllCharacters " + err.message)
                else {
                    Log.d(TAG, "SUCCESS: I got a CharacterDataWrapper $result")
                }
            }

    }
}
