package com.andersclark.marvellissimo.services
import com.andersclark.marvellissimo.entities.MarvelCharacterWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {


// CHARACTERS  //
@GET("characters")
fun getCharacterById(
    @Query("characterId") id: String
): Single<MarvelCharacterWrapper>

    @GET("characters")
    fun getCharacters(
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("name") byExactName: String? = null,
        @Query("orderBy") orderBy: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Single<MarvelCharacterWrapper>

    @GET("characters")
    fun searchCharacter(
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("name") byExactName: String? = null,
        @Query("orderBy") orderBy: String? = "name",
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Single<MarvelCharacterWrapper>

// COMICS  //

    @GET("comics")
    fun getComicById(
        @Query("comicId") id: String
    ): Single<MarvelCharacterWrapper>

    @GET("comics")
    fun getSomeComics(
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("name") byExactName: String? = null,
        @Query("orderBy") orderBy: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Single<MarvelCharacterWrapper>

    @GET("comics")
    fun searchComic(
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("name") byExactName: String? = null,
        @Query("orderBy") orderBy: String? = "name",
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Single<MarvelCharacterWrapper>
}
