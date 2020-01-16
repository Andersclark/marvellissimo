package com.andersclark.marvellissimo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val names = arrayOf("Guy 1", "Guy 2", "Guy 3", "Guy 4","Guy 1", "Guy 2", "Guy 3", "Guy 4")

    private val descriptions = arrayOf("Guy one details", "Guy two details",
        "Guy three details", "Guy four details","Guy one details", "Guy two details",
        "Guy three details", "Guy four details")

    private val thumbnails = intArrayOf(R.drawable.android_image_1,
        R.drawable.android_image_2, R.drawable.android_image_3,
        R.drawable.android_image_4, R.drawable.android_image_1,
        R.drawable.android_image_2, R.drawable.android_image_3,
        R.drawable.android_image_4)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemThumbnail: ImageView
        var itemName: TextView
        var itemDescription: TextView

        init {
            itemThumbnail = itemView.findViewById(R.id.thumbnail)
            itemName = itemView.findViewById(R.id.name)
            itemDescription = itemView.findViewById(R.id.description)

            itemView.setOnClickListener { v: View  ->
                var position: Int = getAdapterPosition()

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
        return names.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemName.text = names[i]
        viewHolder.itemDescription.text = descriptions[i]
        viewHolder.itemThumbnail.setImageResource(thumbnails[i])
    }
}