package com.andersclark.marvellissimo

import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.services.MarvelClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(){
    val searchResults = mutableListOf<MarvelEntity>(
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRecyclerView(charArray)
        checkRadioButtons()
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
    private val chracterList = mutableListOf<MarvelEntity>(
    )

    private fun createRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        val adapter = RecyclerAdapter(testArray)
        recycler_view.adapter = adapter
    }

    private fun checkRadioButtons() {
        group = radioGroup
        var testArray : Array<MarvelCharacter> = charArray

        group.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, _ ->
            if(group.checkedRadioButtonId == 2131230881) {
                createRecyclerView(charArray)
            }
            else if(group.checkedRadioButtonId == 2131230882) {
                createRecyclerView(comicArray)
            }
            else if(group.checkedRadioButtonId == 2131230883) {
                //add code for favorites, when they exist later on
            }
        })
    }

}
