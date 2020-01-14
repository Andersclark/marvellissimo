package com.andersclark.marvellissimo

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.hero_fragment.view.*

class HeroViewHolder (val view: View) : RecyclerView.ViewHolder(view){
    fun setHero(hero : Hero) {
        view.heroName.text = hero.name
    }
}