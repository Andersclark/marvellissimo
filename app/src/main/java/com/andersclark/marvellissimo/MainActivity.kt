package com.andersclark.marvellissimo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.services.MarvelClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity2"
class MainActivity :  RecyclerAdapter.OnItemClickListener, SearchView.OnQueryTextListener, MenuActivity(){

    private lateinit var adapter: RecyclerAdapter
    private var searchResults = mutableListOf<MarvelEntity>()
    private lateinit var group : RadioGroup
    private var marvelData =  MarvelClient.marvelService.getCharacters(limit = 7, nameStartsWith = "spider")
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
    private var searchQuery: String? = "a"
    private var editSearch: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editSearch = findViewById(R.id.search_bar)
        editSearch!!.setOnQueryTextListener(this)

        createRecyclerView(searchResults)
        checkRadioButtons()
        getFavorites()
    }

    private fun createRecyclerView(searchResults: List<MarvelEntity>) {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        adapter = RecyclerAdapter(searchResults, this)
        recycler_view.adapter = adapter
    }

    override fun onCharacterItemClicked(character: MarvelEntity) {
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra("marvelEntity", character)
        startActivity(intent)
    }

    private fun checkRadioButtons() {
        group = radioGroup

        group.setOnCheckedChangeListener { _, _ ->
            when (group.checkedRadioButtonId) {
                radioBtnCharacters.id -> {
                    getMarvelCharacter()
                }
                radioBtnComics.id -> {
                    getMarvelComic()
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Log.d("SEARCH", "searched for $query")
        searchQuery = query
        if(group.checkedRadioButtonId == radioBtnCharacters.id) {
            getMarvelCharacter()
        }
        else if(group.checkedRadioButtonId == radioBtnComics.id) {
            getMarvelComic()
        }
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        //for eventual filtering purposes - reacts on each keystroke
        Log.d("SEARCH", "searching for $newText")
        return false
    }

    private fun getMarvelCharacter() {
        marvelData = MarvelClient.marvelService.getCharacters(limit = 10, nameStartsWith = searchQuery)
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

    private fun getMarvelComic() {
        marvelData = MarvelClient.marvelService.getComics(limit = 10, titleStartsWith = searchQuery)
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

    private fun getFavorites() {
        favoritesSwitch.setOnCheckedChangeListener { _, _ ->
            if(favoritesSwitch.isChecked) Log.d("FAVE", "switch on")
            else Log.d("FAVE", "switch off")
        }
    }
}
