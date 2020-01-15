package com.andersclark.marvellissimo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRecyclerView()
    }

    private fun createRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        val adapter = RecyclerAdapter()
        recycler_view.adapter = adapter
    }

}

