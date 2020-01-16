package com.andersclark.marvellissimo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andersclark.marvellissimo.entities.MarvelCharacter
import com.andersclark.marvellissimo.services.MarvelClient
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
private const val TAG = "RecyclerAdapter"

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    // TODO: Remove dummy-data
    private val thumbnails = intArrayOf(R.drawable.android_image_1,
        R.drawable.android_image_2, R.drawable.android_image_3,
        R.drawable.android_image_4, R.drawable.android_image_1,
        R.drawable.android_image_2, R.drawable.android_image_3)

    private var results =  MarvelClient.marvelService.getCharacters(limit = 7, nameStartsWith = "spider")
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { result, err ->
            if (err?.message != null)
                Log.d(TAG, "GET-FAIL: " + err.message)
            else {
                Log.d(TAG, "GET-SUCCESS: I got a CharacterDataWrapper $result")
                chracterList.addAll(result.data.results)
            }
        }
    private val chracterList = mutableListOf<MarvelCharacter>(
      )

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
        return chracterList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemName.text = chracterList[i].name
        viewHolder.itemDescription.text = chracterList[i].description

        // TODO: Get image from characterList[i].thumbnail.path"
        viewHolder.itemThumbnail.setImageResource(thumbnails[i])
    }
}