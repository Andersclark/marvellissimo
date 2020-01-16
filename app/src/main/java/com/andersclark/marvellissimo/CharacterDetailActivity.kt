package com.andersclark.marvellissimo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character_details.*
import kotlinx.android.synthetic.main.activity_main.*

class CharacterDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        backToMain.setOnClickListener {
            finish()
        }

        /*val character: MarvelCharacter = intent.getSerializableExtra("character") as MarvelCharacter
        nameTitle.text = character.name
        description.text=character.description
        //and later add image ref too

         */

        Picasso.get()
            .load("https://cdn.pixabay.com/photo/2016/11/09/16/24/virus-1812092_1280.jpg")
            .resize(250, 250)
            .centerCrop()
            .placeholder(R.drawable.android_image_1)
            .into(imageView)

    }

}