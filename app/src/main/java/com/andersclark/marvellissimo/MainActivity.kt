package com.andersclark.marvellissimo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.services.MarvelClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener, SearchView.OnQueryTextListener{

    private lateinit var adapter: RecyclerAdapter
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
    private var searchQuery: String? = "re"
    private var editSearch: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editSearch = findViewById(R.id.search_bar)
        editSearch!!.setOnQueryTextListener(this)

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
                getMarvelCharacter()
            }
            else if(group.checkedRadioButtonId == 2131230882) {
                getMarvelComic()
            }
            else if(group.checkedRadioButtonId == 2131230883) {
                //add code for favorites, when they exist later on
            }
        })
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Log.d("SEARCH", "searched for $query")
        searchQuery = query
        if(group.checkedRadioButtonId == 2131230881) {
            getMarvelCharacter()
        }
        else if(group.checkedRadioButtonId == 2131230882) {
            getMarvelComic()
        }
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Log.d("SEARCH", "searching for $newText")
        return false
    }

    fun getMarvelCharacter() {
        marvelData = MarvelClient.marvelService.getCharacters(limit = 7, nameStartsWith = searchQuery)
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

    fun getMarvelComic() {
        marvelData = MarvelClient.marvelService.getComics(limit = 7, titleStartsWith = searchQuery)
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

}
