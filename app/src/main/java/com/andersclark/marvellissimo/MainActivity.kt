package com.andersclark.marvellissimo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.services.MarvelClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Case
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*

lateinit var userFavorites: RealmResults<MarvelEntity>
private const val TAG = "MainActivity"

class MainActivity : RecyclerAdapter.OnItemClickListener, SearchView.OnQueryTextListener,
    MenuActivity() {

    private lateinit var adapter: RecyclerAdapter
    private val realm = Realm.getDefaultInstance()
    private var searchResults = mutableListOf<MarvelEntity>()
    private lateinit var group: RadioGroup
    private var searchQuery: String? = "Wolverine"
    private var editSearch: SearchView? = null
    private val userUid = FirebaseAuth.getInstance().currentUser?.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userFavorites = getFavorites()
        editSearch = findViewById(R.id.search_bar)
        editSearch!!.setOnQueryTextListener(this)
        createRecyclerView(searchResults)
        getMarvelCharacter()
        checkRadioButtons()
        checkFavoriteSwitch()
    }

    private fun createRecyclerView(searchResults: List<MarvelEntity>) {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        adapter = RecyclerAdapter(
            searchResults,
            this
        )
        recycler_view.adapter = adapter
    }

    override fun onCharacterItemClicked(character: MarvelEntity) {
        Log.d("myTag","onCharacterItemClicked "+ character.urls)
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra("id", character.id)
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
        searchQuery = query
        if (group.checkedRadioButtonId == radioBtnCharacters.id) {
            getMarvelCharacter()
        } else if (group.checkedRadioButtonId == radioBtnComics.id) {
            getMarvelComic()
        }
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Log.d("SEARCH", "searching for $newText")
        return false
    }

    private fun getMarvelCharacter() {
        searchResults.clear()
        if (!favoritesSwitch.isChecked) {
            val realmResult = searchInRealm(searchQuery, true)

            if(realmResult.size < 10){
                var marvelData =
                    MarvelClient.marvelService.getCharacters(limit = 10, nameStartsWith = searchQuery)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result, err ->
                            if (err?.message != null){
                                Log.d(TAG, "GET-FAIL: " + err.message)
                                searchResults.addAll(realmResult)
                                adapter.notifyDataSetChanged()
                            }else {
                                Log.d(TAG, "GET-SUCCESS: I got a CharacterDataWrapper $result")
                                searchResults.addAll(result.data.results)
                                saveToRealm(result.data.results)
                                adapter.notifyDataSetChanged()
                            }
                        }
            }else{
                searchResults.addAll(realmResult)
                adapter.notifyDataSetChanged()
            }
        } else {
            for (item in userFavorites)
                if (item.name.isNotBlank()) {
                    searchResults.add(item)
                }
            adapter.notifyDataSetChanged()
        }
    }

    private fun getMarvelComic() {
        searchResults.clear()
        if (!favoritesSwitch.isChecked) {
            val realmResult = searchInRealm(searchQuery, false)
            if(realmResult.size < 10){
                var marvelData =
                    MarvelClient.marvelService.getComics(limit = 10, titleStartsWith = searchQuery)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result, err ->
                            if (err?.message != null){
                                Log.d(TAG, "GET-FAIL: " + err.message)
                                searchResults.addAll(realmResult)
                                adapter.notifyDataSetChanged()
                            } else {
                                Log.d(TAG, "GET-SUCCESS: I got a ComicDataWrapper $result")
                                saveToRealm(result.data.results)
                                searchResults.addAll(result.data.results)
                                adapter.notifyDataSetChanged()
                            }
                        }
            } else {
                searchResults.addAll(realmResult)
                adapter.notifyDataSetChanged()
            }
        } else {
            for (item in userFavorites)
                if (item.title.isNotBlank()) {
                    searchResults.add(item)
                }
            adapter.notifyDataSetChanged()
        }
    }


    private fun getFavorites(): RealmResults<MarvelEntity> {
        val query = realm.where<MarvelEntity>().equalTo("isFavorite", true)
        return query.findAll()
    }

    private fun checkFavoriteSwitch() {
        favoritesSwitch.setOnCheckedChangeListener { _, _ ->
            if (group.checkedRadioButtonId == radioBtnCharacters.id) getMarvelCharacter()
            else getMarvelComic()
        }
    }

    private fun saveToRealm(searchResults: List<MarvelEntity>){
        for(entity in searchResults){
            realm.beginTransaction()
            realm.insertOrUpdate(entity)
            realm.commitTransaction()
        }
    }

    private fun searchInRealm(startsWith: String?, searchForCharacter: Boolean) : RealmResults<MarvelEntity> {
        if (searchForCharacter){
            return realm.where<MarvelEntity>().beginsWith("name", startsWith, Case.INSENSITIVE).findAll()
        } else {
            return realm.where<MarvelEntity>().beginsWith("title", startsWith, Case.INSENSITIVE).findAll()
        }
    }
}
