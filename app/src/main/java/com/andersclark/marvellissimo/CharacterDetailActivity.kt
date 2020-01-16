package com.andersclark.marvellissimo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_character_details.*

class CharacterDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        backToMain.setOnClickListener {
            finish()
        }

        val character: MarvelCharacter = intent.getSerializableExtra("character") as MarvelCharacter
        nameTitle.text = character.name
        description.text=character.description
        //and later add image ref too

    }

}