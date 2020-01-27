package com.andersclark.marvellissimo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.entities.User
import com.andersclark.marvellissimo.services.MarvelClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
lateinit var userFavorites: RealmResults<MarvelEntity>
lateinit var activeUser: User
private const val TAG = "MainActivity2"
class MainActivity :  RecyclerAdapter.OnItemClickListener, SearchView.OnQueryTextListener, MenuActivity(){

    private lateinit var adapter: RecyclerAdapter
    private val realm = Realm.getDefaultInstance()
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
    private val userUid = FirebaseAuth.getInstance().currentUser?.uid
    private val ref:DatabaseReference = FirebaseDatabase.getInstance().getReference("/users/$userUid")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userFavorites = getFavorites()
        editSearch = findViewById(R.id.search_bar)
        editSearch!!.setOnQueryTextListener(this)

        createRecyclerView(searchResults)
        checkRadioButtons()

        Log.d("TESTING AGAIN", ref.toString())

        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w("ACTIVE USER", p0.details)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    activeUser = p0.getValue(User::class.java)!!
                    Log.d("ACTIVE USER", "active user is ${activeUser.username}")
                }
            }
        })
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

    private fun getFavorites(): RealmResults<MarvelEntity> {
        val query = realm.where<MarvelEntity>()
        val results = query.findAll()
        return results
    }

    private fun checkFavoriteSwitch(){
        /*favoritesSwitch.setOnCheckedChangeListener { _, _ ->
            if(favoritesSwitch.isChecked) Log.d("FAVE", "switch on"){
                if (group.checkedRadioButtonId == radioBtnCharacters.id){
                    // searchResult = list of favorite characters
                }
            }
            else Log.d("FAVE", "switch off")
        }*/

        // TODO: Create list from filtering favorites on name.isEmpty() = comic
        // TODO: Create list from filtering favorites on title.isEmpty() = character

    }
}
