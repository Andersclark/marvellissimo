package com.andersclark.marvellissimo

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_layout.view.*

private const val TAG = "RecyclerAdapter"

class RecyclerAdapter(
    private var searchResults: List<MarvelEntity>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onCharacterItemClicked(character: MarvelEntity)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var itemThumbnail: ImageView = itemView.thumbnail
        var itemName: TextView = itemView.name
        var itemDescription: TextView = itemView.description
        var faveBtn: ImageButton = itemView.favoriteButton

        fun bind(character: MarvelEntity) {
            if (character.name != null) {
                itemName.text = character.name
            } else itemName.text = character.title

            itemDescription.text = character.description

            val imagePath =
                character.thumbnail.path + "/portrait_medium." + character.thumbnail.extension
            val safeImagePath = imagePath.replace("http", "https")
            Picasso.get().load(safeImagePath).into(itemThumbnail)

            itemView.setOnClickListener {
                itemClickListener.onCharacterItemClicked(character)
            }

            faveBtn.setOnClickListener {
                toggleFavorite(character, faveBtn)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(searchResults[i])
    }

    private fun toggleFavorite(character: MarvelEntity, faveBtn: ImageButton) {
        Log.d("FAVE", "clicked fave: ${character.name}")
        Log.d("FAVE", "Favorites size: ${userFavorites.size}")
        if (!userFavorites.contains(character)) {
            Log.d("FAVE", "adding favorite")
            userFavorites.add(character)
            faveBtn.setColorFilter(Color.YELLOW)
        } else {
            userFavorites.remove(character)
            faveBtn.setColorFilter(Color.GRAY)
        }
    }

}