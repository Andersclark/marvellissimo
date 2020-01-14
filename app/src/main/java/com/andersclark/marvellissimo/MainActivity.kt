package com.andersclark.marvellissimo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HeroRecyclerView.OnItemClickListener {

    private lateinit var heroRecyclerView: HeroRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRecyclerView()
    }

    private fun createRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val heroes =
            arrayOf(Hero(), Hero("Spiderman"), Hero("Muscle Man"), Hero("Another Strong Guy"))

        heroRecyclerView = HeroRecyclerView(heroes, this)
        recyclerView.adapter = heroRecyclerView
    }

    override fun onHeroItemClicked(hero: Hero) {
        Toast.makeText(
            this,
            "${hero.name}",
            Toast.LENGTH_SHORT
        ).show()
    }
}

