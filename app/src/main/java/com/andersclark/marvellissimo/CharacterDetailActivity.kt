package com.andersclark.marvellissimo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.entities.MarvelEntityThumbnail
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

        val character: MarvelEntity = intent.getSerializableExtra("character") as MarvelEntity
        nameTitle.text = character.name
        description.text = character.description
        //and later add image ref too

        //val imagePath = character.thumbnail.path +"/portrait_xlarge."+character.thumbnail?.extension
       // val safeImagePath=imagePath.replace("http", "https")
        //Picasso.get().load(safeImagePath).into(characterImage)




    }

}