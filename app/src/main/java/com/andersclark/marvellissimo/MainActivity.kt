package com.andersclark.marvellissimo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.services.MarvelClient
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(){

    var searchResults = mutableListOf<MarvelEntity>()
    private lateinit var group : RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createRecyclerView(searchResults)
        checkRadioButtons()
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

    var marvelData =  MarvelClient.marvelService.getCharacters(limit = 7, nameStartsWith = "spider")
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { result, err ->
            if (err?.message != null)
                Log.d(TAG, "GET-FAIL: " + err.message)
            else {
                Log.d(TAG, "GET-SUCCESS: I got a CharacterDataWrapper $result")
                searchResults.addAll(result.data.results)
            }
        }

    private fun createRecyclerView(searchResults: List<MarvelEntity>) {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        val adapter = RecyclerAdapter(searchResults)
        recycler_view.adapter = adapter
    }

    private fun checkRadioButtons() {
        Log.d("RADIO", "here")
        group = radioGroup

        group.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, _ ->
            if(group.checkedRadioButtonId == 2131230881) {
                Log.d("RADIO", "1")
                var marvelData =  MarvelClient.marvelService.getCharacters(limit = 10, nameStartsWith = "b")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result, err ->
                        if (err?.message != null)
                            Log.d(TAG, "GET-FAIL: " + err.message)
                        else {
                            Log.d(TAG, "GET-SUCCESS: I got a CharacterDataWrapper $result")
                            searchResults.addAll(result.data.results)
                        }
                    }
                createRecyclerView(searchResults)
            }
            else if(group.checkedRadioButtonId == 2131230882) {
                Log.d("RADIO", "2")
                var marvelData =  MarvelClient.marvelService.getComics(limit = 21)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result, err ->
                        if (err?.message != null)
                            Log.d(TAG, "GET-FAIL: " + err.message)
                        else {
                            Log.d(TAG, "GET-SUCCESS: I got a CharacterDataWrapper $result")
                            searchResults.addAll(result.data.results)
                        }
                    }
                createRecyclerView(searchResults)
            }
            else if(group.checkedRadioButtonId == 2131230883) {
                //add code for favorites, when they exist later on
            }
        })
    }

}
