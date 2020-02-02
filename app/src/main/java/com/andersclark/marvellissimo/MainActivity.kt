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
//lateinit var activeUser: User
private const val TAG = "MainActivity2"

class MainActivity : RecyclerAdapter.OnItemClickListener, SearchView.OnQueryTextListener,
    MenuActivity() {

    private lateinit var adapter: RecyclerAdapter
    private val realm = Realm.getDefaultInstance()
    private var searchResults = mutableListOf<MarvelEntity>()
    private lateinit var group: RadioGroup
    private var searchQuery: String? = "a"
    private var editSearch: SearchView? = null
    private val userUid = FirebaseAuth.getInstance().currentUser?.uid
    private val ref: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("/users/$userUid")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userFavorites = getFavorites()
        editSearch = findViewById(R.id.search_bar)
        editSearch!!.setOnQueryTextListener(this)
        getDefaultList()
        createRecyclerView(searchResults)
        checkRadioButtons()
        checkFavoriteSwitch()


        /*   ref.addValueEventListener(object : ValueEventListener {
               override fun onCancelled(p0: DatabaseError) {
                   Log.w("ACTIVE USER", p0.details)
               }

               override fun onDataChange(p0: DataSnapshot) {
                   if (p0.exists()) {
                       activeUser = p0.getValue(User::class.java)!!
                       Log.d("ACTIVE USER", "active user is ${activeUser.username}")
                   }
               }
           })*/
    }

    private fun getDefaultList(){
        val realmResult = searchInRealm("a", true)
        if(realmResult.size < 15){
            var marvelData =
                MarvelClient.marvelService.getCharacters(limit = 15, nameStartsWith = "a")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result, err ->
                        if (err?.message != null){
                            searchResults.addAll(realmResult)
                        }else {
                            searchResults.addAll(result.data.results)
                            saveToRealm(result.data.results)
                            adapter.notifyDataSetChanged()
                        }
                    }
        }else{
            searchResults.addAll(realmResult)
        }
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
            marvelData =
                MarvelClient.marvelService.getCharacters(limit = 10, nameStartsWith = searchQuery)
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
            marvelData =
                MarvelClient.marvelService.getComics(limit = 10, titleStartsWith = searchQuery)
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

    private fun searchInRealm(startsWith: String?, searchForCharacter: Boolean) : RealmResults<MarvelEntity>{
        val result: RealmResults<MarvelEntity>

        if(searchForCharacter){
            result = realm.where<MarvelEntity>().beginsWith("name", startsWith, Case.INSENSITIVE).findAll()
            Log.d("RealmTag", "searchInRealm for $startsWith size: "+result.size )

        }else{
            result = realm.where<MarvelEntity>().beginsWith("title", startsWith, Case.INSENSITIVE).findAll()
            Log.d("RealmTag", "searchInRealm for $startsWith: "+result )

        }
        return result
    }
}
