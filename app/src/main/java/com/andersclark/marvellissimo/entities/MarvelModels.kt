package com.andersclark.marvellissimo.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MarvelCharacterWrapper(
    @Expose
    @SerializedName("code")
    val code:String,
    @Expose
    @SerializedName("status")
    val status: String,
    @Expose
    @SerializedName("copyright")
    val copyright: String,
    @Expose
    @SerializedName("attributionText")
    val attributionText: String,
    @Expose
    @SerializedName("attributionHTML")
    val attributionHTML: String,
    @Expose
    @SerializedName("etag")
    val etag: String,
    @Expose
    @SerializedName("data")
    val data: MarvelCharacterList
)

data class MarvelCharacterList(
    @Expose
    @SerializedName("offset")
    val offset:String,
    @Expose
    @SerializedName("total")
    val total:String,
    @Expose
    @SerializedName("count")
    val count:String,
    @Expose
    @SerializedName("results")
    val results: List<MarvelCharacter>)

data class MarvelCharacter(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("thumbnail")
    val thumbnail: MarvelCharacterThumbnail,
    @Expose
    @SerializedName("resourceURI")
    val resourceURI: String
)

data class MarvelCharacterThumbnail(
    @Expose
    @SerializedName("path")
    val path: String,
    @Expose
    @SerializedName("extension")
    val extension: String)




