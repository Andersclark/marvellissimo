package com.andersclark.marvellissimo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.services.MarvelClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(){
    private val searchResults = mutableListOf<MarvelEntity>(
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createRecyclerView(searchResults)
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
}
