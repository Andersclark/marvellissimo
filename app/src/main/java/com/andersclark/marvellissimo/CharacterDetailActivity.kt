package com.andersclark.marvellissimo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character_details.*

class CharacterDetailActivity: MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        val marvelEntity: MarvelEntity = intent.getSerializableExtra("marvelEntity") as MarvelEntity
        goToBtn.setOnClickListener{goToUrl(marvelEntity.urls.get(0).url)}
        nameTitle.text = if(marvelEntity.name.isEmpty()) {marvelEntity.title} else {marvelEntity.name}
        description.text = marvelEntity.description

        val imagePath=marvelEntity.thumbnail.path+"/portrait_xlarge."+marvelEntity.thumbnail.extension
        val safeImagePath=imagePath.replace("http", "https")
        Picasso.get().load(safeImagePath).into(characterImage)

    }
    
    private fun goToUrl(url: String) {
        val uriUrl: Uri = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }
}