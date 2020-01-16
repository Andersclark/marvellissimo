package com.andersclark.marvellissimo

import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRecyclerView()
    }

    private fun createRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        val adapter = RecyclerAdapter(checkRadioButtons())
        recycler_view.adapter = adapter
    }

    private fun checkRadioButtons(): Array<MarvelCharacter> {
        group = radioGroup
        var testArray : Array<MarvelCharacter> = charArray

        group.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, _ ->
            if(group.checkedRadioButtonId == 2131230881) {
                Log.d("RADIO", "Character")
                testArray = charArray
            }
            else if(group.checkedRadioButtonId == 2131230882) {
                Log.d("RADIO", "Comic")
                testArray = charArray
            }
            else if(group.checkedRadioButtonId == 2131230883) {
                Log.d("RADIO", "Favorite")
                testArray = charArray
            }
        })
        return testArray
    }

}

