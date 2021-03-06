package com.andersclark.marvellissimo.services
import com.andersclark.marvellissimo.entities.MarvelResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {


    @GET("characters")
    fun getCharacterById(
        @Query("characterId") id: String
    ): Single<MarvelResponse>

    @GET("characters")
    fun getCharacters(
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("name") byExactName: String? = null,
        @Query("orderBy") orderBy: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Single<MarvelResponse>

    @GET("characters")
    fun searchCharacter(
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("name") byExactName: String? = null,
        @Query("orderBy") orderBy: String? = "name",
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Single<MarvelResponse>


    @GET("comics")
    fun getComicById(
        @Query("comicId") id: String
    ): Single<MarvelResponse>

    @GET("comics")
    fun getComics(
        @Query("titleStartsWith") titleStartsWith: String? = null,
        @Query("title") byExactTitle: String? = null,
        @Query("orderBy") orderBy: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Single<MarvelResponse>

    @GET("comics")
    fun searchComic(
        @Query("titleStartsWith") titleStartsWith: String? = null,
        @Query("title") byExactTitle: String? = null,
        @Query("orderBy") orderBy: String? = "name",
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Single<MarvelResponse>
}
