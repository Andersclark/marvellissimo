package com.andersclark.marvellissimo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter (
   private val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    interface OnItemClickListener { fun onCharacterItemClicked(character: MarvelCharacter)  }

    private val thumbnails = intArrayOf(R.drawable.android_image_1,
        R.drawable.android_image_2, R.drawable.android_image_3,
        R.drawable.android_image_4, R.drawable.android_image_1,
        R.drawable.android_image_2, R.drawable.android_image_3)

    private val testChar = arrayOf(MarvelCharacter("itemId", "Spiderman", "Here's a short description of this character", "resourceURI"),
        MarvelCharacter("itemId", "Superman", "Here's a short description of this character", "resourceURI"),
        MarvelCharacter("itemId", "The Hulk", "Here's a short description of this character", "resourceURI"),
        MarvelCharacter("itemId", "Raccoon guy", "Here's a short description of this character", "resourceURI"),
        MarvelCharacter("itemId", "Groot", "Here's a short description of this character", "resourceURI"),
        MarvelCharacter("itemId", "Flash", "Here's a short description of this character", "resourceURI"),
        MarvelCharacter("itemId", "Mr. Fantastic", "Here's a short description of this character", "resourceURI"))

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var itemThumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        var itemName: TextView= itemView.findViewById(R.id.name)
        var itemDescription: TextView=itemView.findViewById(R.id.description)

         fun bind(character: MarvelCharacter) {
             itemName.text = character.name
             itemDescription.text = character.description
             //change to char array eventually
            // itemThumbnail.setImageResource(thumbnails[i])

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
        return testChar.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(testChar[i])
    }
}