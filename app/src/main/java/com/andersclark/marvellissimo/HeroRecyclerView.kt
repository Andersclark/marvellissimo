package com.andersclark.marvellissimo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.hero_fragment.view.*

class HeroRecyclerView(
    private val heroes: Array<Hero>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HeroRecyclerView.ViewHolder>() {

    val xml_layout = R.layout.hero_fragment;

    interface OnItemClickListener {
        fun onHeroItemClicked(hero: Hero)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.heroName

        fun bind(hero: Hero) {
            name.text = hero.name

            itemView.setOnClickListener {
                itemClickListener.onHeroItemClicked(hero)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(xml_layout, parent, false))

    override fun getItemCount(): Int = heroes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(heroes[position])
    }
}
