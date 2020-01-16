package com.andersclark.marvellissimo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class RecyclerAdapter(checkRadioButtons: Array<MarvelCharacter>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var testArray = checkRadioButtons
    private val thumbnails = intArrayOf(R.drawable.android_image_1,
        R.drawable.android_image_2, R.drawable.android_image_3,
        R.drawable.android_image_4, R.drawable.android_image_1,
        R.drawable.android_image_2, R.drawable.android_image_3)


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemThumbnail: ImageView
        var itemName: TextView
        var itemDescription: TextView

        init {
            itemThumbnail = itemView.findViewById(R.id.thumbnail)
            itemName = itemView.findViewById(R.id.name)
            itemDescription = itemView.findViewById(R.id.description)

            itemView.setOnClickListener { v: View  ->
                var position: Int = adapterPosition

                Snackbar.make(v, "Clicked guy at position $position",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return testArray.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemName.text = testArray[i].name
        viewHolder.itemDescription.text = testArray[i].description
        //change to char array eventually
        viewHolder.itemThumbnail.setImageResource(thumbnails[i])
    }
}