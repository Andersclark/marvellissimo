package com.andersclark.marvellissimo.entities.realm

import com.andersclark.marvellissimo.entities.MarvelEntity
import com.andersclark.marvellissimo.entities.MarvelEntityThumbnail
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/* TODO: Use for caching API-calls

open class RealmMarvelResponseData(
    var offset:String = "",
    var total:String = "",
    var count:String = "",
    var results: RealmList<RealmMarvelEntity> = RealmList() ): RealmObject()

*/


open class RealmMarvelEntity(
    @PrimaryKey var id: String = "",
    var name: String = "",
    var title: String = "",
    var description: String = "",
    var thumbnail: RealmMarvelEntityThumbnail? = null,
    var resourceURI: String = ""

): RealmObject(){

    fun getMarvelEntity(): MarvelEntity {
        return MarvelEntity(
            this.id,
            this.name,
            this.title,
            this.description,
            // TODO: Fix objectmapping of thumbnail
            thumbnail = MarvelEntityThumbnail("", "")

            /*{
                if (this.thumbnail != null) this.thumbnail.getMarvelEntityThumbnail()
                else MarvelEntityThumbnail("", "")
            }*/,

            resourceURI = this.resourceURI
            )
        }
    }

open class RealmMarvelEntityThumbnail(
    var path: String = "",
    var extension: String = "") : RealmObject(){


    fun getMarvelEntityThumbnail(): MarvelEntityThumbnail {
        return MarvelEntityThumbnail(this.path, this.extension)
    }
}




