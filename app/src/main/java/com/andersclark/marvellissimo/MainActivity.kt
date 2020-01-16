package com.andersclark.marvellissimo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRecyclerView()
    }

    private fun createRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        val adapter = RecyclerAdapter(this)
        recycler_view.adapter = adapter
    }

    override fun onCharacterItemClicked(character: MarvelCharacter) {
        Toast.makeText(
            this,
            "${character.name} ",
            Toast.LENGTH_SHORT
        ).show()
    }
    
}

