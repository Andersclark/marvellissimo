package com.andersclark.marvellissimo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.services.MarvelClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener{

    lateinit var adapter: RecyclerAdapter
    var searchResults = mutableListOf<MarvelEntity>()
    private lateinit var group : RadioGroup
    var marvelData =  MarvelClient.marvelService.getCharacters(limit = 7, nameStartsWith = "spider")
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { result, err ->
            if (err?.message != null)
                Log.d(TAG, "GET-FAIL: " + err.message)
            else {
                Log.d(TAG, "GET-SUCCESS: I got a CharacterDataWrapper $result")
                searchResults.addAll(result.data.results)
                adapter.notifyDataSetChanged()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRecyclerView(searchResults)
        checkRadioButtons()
    }

    private fun createRecyclerView(searchResults: List<MarvelEntity>) {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        adapter = RecyclerAdapter(searchResults, this)
        recycler_view.adapter = adapter
    }

    override fun onCharacterItemClicked(character: MarvelEntity) {
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra("character", character)
        startActivity(intent)
    }


    private fun checkRadioButtons() {
        group = radioGroup

        group.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, _ ->
            if(group.checkedRadioButtonId == 2131230881) {
                Log.d("RADIO", "1")
                marvelData = MarvelClient.marvelService.getCharacters(limit = 7, nameStartsWith = "b")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result, err ->
                        if (err?.message != null)
                            Log.d(TAG, "GET-FAIL: " + err.message)
                        else {
                            Log.d(TAG, "GET-SUCCESS: I got a CharacterDataWrapper $result")
                            searchResults.clear()
                            searchResults.addAll(result.data.results)
                            adapter.notifyDataSetChanged()
                        }
                    }
            }
            else if(group.checkedRadioButtonId == 2131230882) {
                Log.d("RADIO", "2")
                marvelData = MarvelClient.marvelService.getComics(limit = 7, titleStartsWith = "m")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result, err ->
                        if (err?.message != null)
                            Log.d(TAG, "GET-FAIL: " + err.message)
                        else {
                            Log.d(TAG, "GET-SUCCESS: I got a CharacterDataWrapper $result")
                            searchResults.clear()
                            searchResults.addAll(result.data.results)
                            adapter.notifyDataSetChanged()
                        }
                    }
            }
            else if(group.checkedRadioButtonId == 2131230883) {
                //add code for favorites, when they exist later on
            }
        })
    }

}
