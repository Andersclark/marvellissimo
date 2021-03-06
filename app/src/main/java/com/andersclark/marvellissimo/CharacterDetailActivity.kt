package com.andersclark.marvellissimo

import android.content.Intent
import android.os.Bundle
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_character_details.*


class CharacterDetailActivity: MenuActivity() {
    private val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        val entityId = intent.getStringExtra("id")
        val marvelEntity =realm.where<MarvelEntity>().equalTo("id", entityId).findFirst()
        if(marvelEntity != null){
            nameTitle.text = if(marvelEntity.name.isEmpty()) {marvelEntity.title} else {marvelEntity.name}
            description.text = marvelEntity.description
            val imagePath=marvelEntity.thumbnail!!  .path+"/portrait_xlarge."+marvelEntity.thumbnail!!.extension
            val safeImagePath=imagePath.replace("http", "https")
            Picasso.get().load(safeImagePath).into(characterImage)

            if(marvelEntity.urls.size > 0){
                goToBtn.setOnClickListener{goToUrl(marvelEntity.urls.get(0)!!.url)}
            }else{
                goToBtn.setOnClickListener{goToUrl("https://www.marvel.com/")}
            }
        }
    }
    
    private fun goToUrl(url: String) {
        val safeUrl = if(!url.startsWith("https")) url.replace("http", "https") else url
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", safeUrl)
        startActivity(intent)
    }
}