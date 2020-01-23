package com.andersclark.marvellissimo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character_details.*
import kotlinx.android.synthetic.main.activity_main.*

class CharacterDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        val marvelEntity: MarvelEntity = intent.getSerializableExtra("marvelEntity") as MarvelEntity
            finish()
        }

        nameTitle.text = if(marvelEntity.name == null) marvelEntity.title else marvelEntity.name
        goToBtn.text= if(marvelEntity.name == null)"Learn more about this comic on marvels webpage" else "Learn more about this character on marvels webpage"
        description.text=marvelEntity.description
        //and later add image ref too

        val imagePath=marvelEntity.thumbnail.path+"/portrait_xlarge."+marvelEntity.thumbnail.extension
        val safeImagePath=imagePath.replace("http", "https")
        Picasso.get().load(safeImagePath).into(characterImage)




    }

}