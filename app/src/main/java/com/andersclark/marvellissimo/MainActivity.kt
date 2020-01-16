package com.andersclark.marvellissimo

import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(){

    lateinit var group : RadioGroup

    private val charArray = arrayOf(
        MarvelCharacter(
            "itemId",
            "Spiderman",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "Superman",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "The Hulk",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "Raccoon guy",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "Groot",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "Flash",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "Mr. Fantastic",
            "Here's a short description of this character",
            "resourceURI"
        )
    )

    private val comicArray = arrayOf(
        MarvelCharacter(
            "itemId",
            "Spooderman",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "Sooperman",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "The Hoolk",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "Raccoon goouy",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "Grooooot",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "Floosh",
            "Here's a short description of this character",
            "resourceURI"
        ),
        MarvelCharacter(
            "itemId",
            "Mr. Foontoostooooooc",
            "Here's a short description of this character",
            "resourceURI"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRecyclerView(charArray)
        checkRadioButtons()
    }

    private fun createRecyclerView(testArray: Array<MarvelCharacter>) {
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
