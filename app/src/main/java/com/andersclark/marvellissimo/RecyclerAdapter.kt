package com.andersclark.marvellissimo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andersclark.marvellissimo.entities.MarvelEntity
import com.squareup.picasso.Picasso

private const val TAG = "RecyclerAdapter"

class RecyclerAdapter(private var searchResults: List<MarvelEntity>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    interface OnItemClickListener { fun onCharacterItemClicked(character: MarvelEntity)  }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var itemThumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        var itemName: TextView= itemView.findViewById(R.id.name)
        var itemDescription: TextView=itemView.findViewById(R.id.description)

         fun bind(character: MarvelEntity) {
             if(character.name != null) {
                 itemName.text = character.name
             } else itemName.text = character.title

             itemDescription.text = character.description

             val imagePath=character.thumbnail.path+"/portrait_medium."+character.thumbnail.extension
             val safeImagePath=imagePath.replace("http", "https")
             Picasso.get().load(safeImagePath).into(itemThumbnail)

             itemView.setOnClickListener {
                 itemClickListener.onCharacterItemClicked(character)
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
}