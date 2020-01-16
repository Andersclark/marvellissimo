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

class RecyclerAdapter (
   private val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    interface OnItemClickListener { fun onCharacterItemClicked(character: MarvelCharacter)  }

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
        return chracterList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(testChar[i])
    }
}