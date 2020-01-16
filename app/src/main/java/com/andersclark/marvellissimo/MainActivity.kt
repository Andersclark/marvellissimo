package com.andersclark.marvellissimo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRecyclerView()

        button_showDetails.setOnClickListener {
            val intent = Intent(this, CharacterDetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        val adapter = RecyclerAdapter()
        recycler_view.adapter = adapter
    }
}

