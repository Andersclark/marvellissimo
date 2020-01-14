package com.andersclark.marvellissimo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HeroAdapter(val heroes: Array<Hero>) : RecyclerView.Adapter<HeroViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.hero_fragment, parent, false)

        return HeroViewHolder(view)
    }

    override fun getItemCount() = heroes.size

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.setHero(heroes[position])
    }
}