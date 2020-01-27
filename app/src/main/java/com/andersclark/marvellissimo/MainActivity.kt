package com.andersclark.marvellissimo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.entities.realm.RealmFavorites
import com.andersclark.marvellissimo.entities.realm.RealmMarvelEntity
import com.andersclark.marvellissimo.entities.realm.RealmMarvelEntityThumbnail
import com.andersclark.marvellissimo.services.MarvelClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity2"
class MainActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener, SearchView.OnQueryTextListener{

    private lateinit var adapter: RecyclerAdapter
    private lateinit var group : RadioGroup

    val realm = Realm.getDefaultInstance()
    var searchResults = mutableListOf<MarvelEntity>()
    var favorites = mutableListOf<MarvelEntity>()

    var marvelData =  MarvelClient.marvelService.getCharacters(limit = 20, nameStartsWith = "hulk")
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
        favorites = getLocalFavorites()
        checkRadioButtons()


        // TEST:
        saveFavorites()

    }

    private fun saveFavorites() {
        realm.beginTransaction()
        realm.createObject<RealmMarvelEntity>(RealmMarvelEntity(
            "1",
            "FavoriteOne",
            "",
            "Just another favorite",
            RealmMarvelEntityThumbnail(),
            "someresourceURI"))
        realm.commitTransaction()
    }

    private fun createRecyclerView(searchResults: List<MarvelEntity>) {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        adapter = RecyclerAdapter(searchResults, this)
        recycler_view.adapter = adapter
    }

    override fun onCharacterItemClicked(character: MarvelEntity) {
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra("characterId", character.id)
        startActivity(intent)
    }


    private fun checkRadioButtons() {
        group = radioGroup

        group.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, _ ->
            if(group.checkedRadioButtonId == radioBtnCharacters.id) {
                getMarvelCharacter()
            }
            else if(group.checkedRadioButtonId == radioBtnComics.id) {
                getMarvelComic()
            }
            else if(group.checkedRadioButtonId == radioBtnFavorites.id) {
                searchResults = getLocalFavorites()
            }
        })
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Log.d("SEARCH", "searched for $query")
        searchQuery = query
        if(group.checkedRadioButtonId == radioBtnCharacters.id) {
            getMarvelCharacter()
        }
        else if(group.checkedRadioButtonId == radioBtnFavorites.id) {
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

    private fun getLocalFavorites(): MutableList<MarvelEntity> {
        val realmFavorites = realm.where<RealmFavorites>().findFirst()
        if(realmFavorites != null){
            val result = mutableListOf<MarvelEntity>()
            for(favorite in realmFavorites.favorites){
                val tempFavorite = favorite.getMarvelEntity()
                result.add(tempFavorite)
            }

            return result
        } else return mutableListOf()

    }


}
