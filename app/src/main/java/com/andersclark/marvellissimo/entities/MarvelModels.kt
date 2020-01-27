package com.andersclark.marvellissimo.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.io.Serializable

data class MarvelResponse(
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
    val data: MarvelResponseData
)

data class MarvelResponseData(
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
    val results: List<MarvelEntity>)

open class MarvelEntity(
    @Expose
    @SerializedName("id")
    @PrimaryKey
    var id: String = "",
    var isFavorite: Boolean = false,
    @Expose
    @SerializedName("name")
    var name: String = "",
    @Expose
    @SerializedName("title")
    var title: String = "",
    @Expose
    @SerializedName("description")
    var description: String = "",
    @Expose
    @SerializedName("thumbnail")
    @Ignore // TODO: Implement nested class: MarvelEntityThumbnal
    var thumbnail: MarvelEntityThumbnail = MarvelEntityThumbnail("", ""),
    @Expose
    @SerializedName("resourceURI")
    var resourceURI: String = "",
    @Expose
    @SerializedName("urls")
    @Ignore  // TODO: Implement nested class
    var urls: List<UrlEntity> = mutableListOf())
    :Serializable, RealmObject()

open class UrlEntity(
    @Expose
    @SerializedName("type")
    var type: String = "",
    @Expose
    @SerializedName("url")
    var url: String = "")
    :Serializable, RealmObject()

open class MarvelEntityThumbnail(
    @Expose
    @SerializedName("path")
    var path: String = "",
    @Expose
    @SerializedName("extension")
    var extension: String = ""):Serializable, RealmObject()




